package com.sap.ps.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.WhereJoinTable;

@EdmEntitySet
@EdmEntityType
@Entity
@Table(name = "USERS")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String displayName;
    
    @Column
    private String email;

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "users")
//    @JoinTable(
//            name = "BORROWS",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName="id")
//    )
//    @WhereJoinTable( clause = "status = 'BORROWED'")
    @WhereJoinTable(clause = "state='borrow'")
    private List<Book> books = new ArrayList<Book>();

    public User() {

    }
    
    public User(Long id) {
    	this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
