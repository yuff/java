package com.sap.ps.odata;

import java.util.Locale;

import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sap.icd.odatav2.spring.messages.MessageBuffer;
import com.sap.ps.model.BookRepository;
import com.sap.ps.model.UserRepository;

@Component
public class BookUserOperations implements ApplicationContextAware{
	private static int id = -1;

	private static int getId() {
		return id;
	}

	private static int incrementId() {
		return ++id;
	}
	
	private MessageBuffer messageBuffer;
	
    private  BookRepository bookRepository;
    private  UserRepository userRepository;
    
    private static ApplicationContext applicationContext;

	public MessageBuffer getMessageBuffer() {
		return messageBuffer;
	}

	@Autowired
	public void setMessageBuffer(MessageBuffer messageBuffer) {
		this.messageBuffer = messageBuffer;
	}

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}
	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	private static void setStaticApplicationContext(ApplicationContext applicationContext) {
		if (BookUserOperations.applicationContext == null) {
			BookUserOperations.applicationContext = applicationContext;
		}
	}

	public BookUserOperations() {
		BookUserOperations.incrementId();
		System.out.println("BookUserOperations Instance: " + id);
		ApplicationContext applicationContext = BookUserOperations.getApplicationContext();
		if (applicationContext != null) {
			applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
		}
	}

	@EdmFunctionImport(name = "BorrowBook", returnType = @ReturnType(type = Type.SIMPLE, isCollection = false))
	public Boolean borrowBook(@EdmFunctionImportParameter(name = "UserId") final Long userId,
			@EdmFunctionImportParameter(name = "BookId") final Long bookId) throws ODataException {
		try {
			bookRepository.borrowBook(userId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ODataApplicationException("Internal error", Locale.ENGLISH,
					HttpStatusCodes.INTERNAL_SERVER_ERROR);
		}
		return true;
	}

	@EdmFunctionImport(name = "ReturnBook", returnType = @ReturnType(type = Type.SIMPLE, isCollection = false))
	public Boolean returnBook(@EdmFunctionImportParameter(name = "UserId") final Long userId,
			@EdmFunctionImportParameter(name = "BookId") final Long bookId) throws ODataException {
		try {
			bookRepository.returnBook(userId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ODataApplicationException("Internal error", Locale.ENGLISH,
					HttpStatusCodes.INTERNAL_SERVER_ERROR);
		}
		return true;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BookUserOperations.setStaticApplicationContext(applicationContext);
		System.out.println("setApplicationContext for BookUserOperations Instance: " + BookUserOperations.getId());
	}
}
