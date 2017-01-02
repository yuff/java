package com.sap.ps.model;

import javax.persistence.*;

@Entity
@Table(name = "BORROWS")
public class Borrow {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="book_id", referencedColumnName="id")
    private Book book;

    @Column
    private String status;

    public Borrow() {

    }

    public Borrow(Book book, User user, String status) {
        this.book = book;
        this.user = user;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
