package com.sap.ps.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * TODO: this entity is not necessary currently, we are not using Borrows api currently,
 * leave it here.
 *
 */
@Entity
@Table(name = "BORROWS")
public class Borrow {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;
 
    @ManyToOne(fetch=FetchType.EAGER)
    @PrimaryKeyJoinColumn(name="book_id", referencedColumnName="id")
    private Book book;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
    
}
