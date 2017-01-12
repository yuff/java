package com.sap.ps.model;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sap.ps.LibraryUtil;

/** 
 * 1. the implement of custom repository name of BookRepository should be BookRepository + Impl
 * details：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.core-concepts
 * 2. should add @Transactional annotation, or there will be javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread
 * 
 */
@Transactional
public class BookRepositoryImpl implements BookRepositoryCustom {

	private EntityManagerFactory entityManagerFactory;
    
	private static final String UPDATE_BOOK_STATE = "UPDATE BOOKS b SET STATE = ?1 WHERE ID = ?2";
	private static final String BORROW_BOOK = "INSERT INTO BORROWS (ID, USER_ID,BOOK_ID, STATE, START_DATE) VALUES (S_BORROW.NEXTVAL, ?1, ?2,'borrow', CURRENT_DATE)";
	private static final String RETURN_BOOK = "UPDATE BORROWS br SET STATE = 'return',UPDATE_DATE=CURRENT_DATE WHERE USER_ID = ?1 AND BOOK_ID = ?2 and STATE = 'borrow'";
	private static final String BOOK_STATE_BORROWED = "unavailable";
	private static final String BOOK_STATE_IN_STORE = "available";
    
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public Book updateBook(BookPatch bookPatch) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Book book = new Book(bookPatch);
		Book oldBook = em.find(Book.class, bookPatch.getId());
		BeanUtils.copyProperties(book, oldBook, LibraryUtil.getNullPropertyNames(book));
		if (!StringUtils.isEmpty(bookPatch.getUserId())) {
			User user = em.find(User.class, Long.valueOf(bookPatch.getUserId()));
			//TODO: exact status to enum
			if (!StringUtils.isEmpty(book.getState()) && book.getState().equals("unavailable")) {
				//if status is unavailable, borrow book
				if (!oldBook.getUsers().contains(user)) {
					oldBook.getUsers().add(user);
				}
			} else {
				//if status is null or not equals to BORROWED while user id not null, return book
				oldBook.getUsers().remove(user);
			}
		}

		return em.merge(oldBook);
	}

	@Override
	public Book borrowBook(Long userId, Long bookId) {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query updateBookState = em.createNativeQuery(UPDATE_BOOK_STATE);
		updateBookState.setParameter(1, BOOK_STATE_BORROWED);
		updateBookState.setParameter(2, bookId);
		
//		Query q = em.createNativeQuery("select nextval('S_BORROW')");
//		Long key = ((BigInteger)q.getSingleResult()).longValue();
		Query borrowBook = em.createNativeQuery(BORROW_BOOK);
		borrowBook.setParameter(1, userId);
		borrowBook.setParameter(2, bookId);
		updateBookState.executeUpdate();
		borrowBook.executeUpdate();
		et.commit();
		return em.find(Book.class, bookId);
	}

	@Override
	public Book returnBook(Long userId, Long bookId) {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction(); 
		et.begin();
		Query updateBookState = em.createNativeQuery(UPDATE_BOOK_STATE);
		updateBookState.setParameter(1, BOOK_STATE_IN_STORE);
		updateBookState.setParameter(2, bookId);
		
		Query borrowBook = em.createNativeQuery(RETURN_BOOK);
		borrowBook.setParameter(1, userId);
		borrowBook.setParameter(2, bookId);
		updateBookState.executeUpdate();
		borrowBook.executeUpdate();
		et.commit();
		return em.find(Book.class, bookId);
	}
}
