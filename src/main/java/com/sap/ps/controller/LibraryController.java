package com.sap.ps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ps.model.Book;
import com.sap.ps.model.BookPatch;
import com.sap.ps.model.BookRepository;
import com.sap.ps.model.User;
import com.sap.ps.model.UserRepository;

@RestController
public class LibraryController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    LibraryController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

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

    /***
     * update book, status = null or status != "BORROWED" && userId !== null, return book
     * status = "BORROWED" && userId != null, borrow book
     * */
    @RequestMapping(value="/books/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody BookPatch book) {
        if (book.getId() == null) {
            book.setId(Long.valueOf(id));
        }
        Book result = this.bookRepository.updateBook(book);
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

//    @RequestMapping(value="/borrows", method = RequestMethod.POST)
//    public ResponseEntity<Borrow> borrowBook(@RequestBody Borrow borrow) {
//        Borrow result = this.borrowRepository.save(borrow);
//        return new ResponseEntity<Borrow>(result, HttpStatus.OK);
//    }

//    @RequestMapping(value="/borrows/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Borrow> returnBookById(@PathVariable String id) {
//        Borrow borrow = this.borrowRepository.findOne(Long.valueOf(id));
//        borrow.setStatus("RETURNED");
//        this.borrowRepository.save(borrow);
//        return new ResponseEntity<Borrow>(HttpStatus.NO_CONTENT);
//    }    

}
