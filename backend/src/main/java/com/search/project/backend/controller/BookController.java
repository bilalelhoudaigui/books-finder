package com.search.project.backend.controller;

import java.util.List;

import com.search.project.backend.model.BookDocument;
import com.search.project.backend.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDocument> findAllBooks() throws Exception {
        return bookService.findAll();
    }

    // http://localhost:8080/api/v1/book/search?bookTitle=Harry
    @GetMapping("/search")
    public List<BookDocument> searchGoName(
            @RequestParam(value = "title") String bookTitle)
            throws Exception {
        return bookService.findByBookTitle(bookTitle);
    }
}