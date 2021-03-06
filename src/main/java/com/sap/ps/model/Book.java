package com.sap.ps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@EdmEntitySet
@EdmEntityType
@Entity
@DynamicUpdate
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
			generator = "s_book")
    @SequenceGenerator(name = "s_book", sequenceName = "S_BOOK",
	allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String author;

    @Column
    private String state = "available";

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinTable(
            name = "BORROWS",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName="id")
    )
    @JsonIgnoreProperties("books")
    @WhereJoinTable(clause = "state='borrow'")
    private List<User> users;

    @OneToMany(mappedBy="book")
    @Where(clause="state='return'")
    @JsonIgnoreProperties("book")
    private List<Borrow> borrowHistory;
    
    public Book() {

    }
    
    public Book(Book book){
    	this.id = book.getId();
    	this.title = book.getTitle();
    	this.author = book.getAuthor();
    	this.description = book.getDescription();
    	this.state = book.getState();
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    public Book(Long id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public List<Borrow> getBorrowHistory() {
		return borrowHistory;
	}

	public void setBorrowHistory(List<Borrow> borrowHistory) {
		this.borrowHistory = borrowHistory;
	}

}
