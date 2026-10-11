package com.nguyentrananhquoc.library_backend.controller;

import com.nguyentrananhquoc.library_backend.entity.Book;
import com.nguyentrananhquoc.library_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // API API lấy danh sách: GET http://localhost:8080/api/books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // API thêm sách mới: POST http://localhost:8080/api/books
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}