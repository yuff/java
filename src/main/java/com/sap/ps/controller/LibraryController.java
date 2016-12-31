package com.sap.ps.controller;

import com.sap.ps.LibraryApplication;
import com.sap.ps.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

import com.sap.ps.model.Book;

@RestController
public class LibraryController {

    private final BookRepository bookRepository;

    @Autowired
    LibraryController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

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

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book result = bookRepository.save(book);
        return new ResponseEntity<Book>(result, HttpStatus.OK);
    }

}
