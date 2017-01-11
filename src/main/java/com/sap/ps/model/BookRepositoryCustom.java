package com.sap.ps.model;

import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositoryCustom {
	public Book updateBook(BookPatch bookPatch);
	
	public Book borrowBook(Long userId, Long bookId);
	
	public Book returnBook(Long userId, Long bookId);
}
