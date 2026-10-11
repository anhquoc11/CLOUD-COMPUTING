package com.nguyentrananhquoc.library_backend.service;

import com.nguyentrananhquoc.library_backend.entity.Book;
import com.nguyentrananhquoc.library_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    // Lấy danh sách tất cả các sách
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Thêm một cuốn sách mới
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}