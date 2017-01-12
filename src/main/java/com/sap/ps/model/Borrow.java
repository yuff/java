package com.sap.ps.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * TODO: this entity is not necessary currently, we are not using Borrows api currently,
 * leave it here.
 *
 */
@Entity
@Table(name = "BORROWS")
public class Borrow {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
			generator = "s_borrow")
    @SequenceGenerator(name = "s_borrow", sequenceName = "S_BORROW",
	allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;
 
    @ManyToOne(fetch=FetchType.EAGER)
    @PrimaryKeyJoinColumn(name="book_id", referencedColumnName="id")
    private Book book;

    @Column
    private String state;
    
    @Column
    @Type(type="date")
    private Date startDate;
    
    @Column
    @Type(type="date")
    private Date updateDate;
    
    @PrePersist
    protected void onCreate() {
    	startDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	updateDate = new Date();
    }
    
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
    
}
