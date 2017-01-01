package com.sap.ps.controller;

import com.sap.ps.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
public class LibraryController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    @Autowired
    LibraryController(BookRepository bookRepository, UserRepository userRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;

    }

    @RequestMapping("/books")
    public List<Book> getBooks(@RequestParam(value="title", defaultValue="")String title) {
        if (StringUtils.isEmpty(title)) {
            return this.bookRepository.findAll();
        } else {
            return this.bookRepository.findByTitle(title);
        }
    }

    @RequestMapping("/books/{id}")
    public Book findBookById(@PathVariable String id) {
        return this.bookRepository.findOne(Long.valueOf(id));
    }

    @RequestMapping(value="/books/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        if (book.getId() == null) {
            book.setId(Long.valueOf(id));
        }
        Book result = this.bookRepository.save(book);
        return new ResponseEntity<Book>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book result = this.bookRepository.save(book);
        return new ResponseEntity<Book>(result, HttpStatus.OK);
    }

    @RequestMapping("/users")
    public List<User> findAllUsers(@RequestParam(value="name", defaultValue="")String name) {
        if (StringUtils.isEmpty(name)) {
            return this.userRepository.findAll();
        } else {
            return this.userRepository.findByName(name);
        }
    }

    @RequestMapping(value="/users", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User result = this.userRepository.save(user);
        return new ResponseEntity<User>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/borrows", method = RequestMethod.POST)
    public ResponseEntity<Borrow> borrowBook(@RequestBody Borrow borrow) {
        Borrow result = this.borrowRepository.save(borrow);
        return new ResponseEntity<Borrow>(result, HttpStatus.OK);
    }

}
