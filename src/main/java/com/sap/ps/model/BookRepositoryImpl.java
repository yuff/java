package com.sap.ps.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
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

    @PersistenceContext
    private EntityManager em;
    
	@Override
	public Book updateBook(BookPatch bookPatch) {
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
}
