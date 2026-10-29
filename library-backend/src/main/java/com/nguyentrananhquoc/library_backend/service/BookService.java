package com.nguyentrananhquoc.library_backend.service;

import com.nguyentrananhquoc.library_backend.entity.Book;
import com.nguyentrananhquoc.library_backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 1. Lấy danh sách tất cả các sách
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 2. Lấy chi tiết sách theo ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // 3. Thêm một cuốn sách mới
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // 4. Cập nhật thông tin sách
    public Book updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setAvailable(bookDetails.isAvailable());
            if (bookDetails.getCoverImageUrl() != null) {
                book.setCoverImageUrl(bookDetails.getCoverImageUrl());
            }
            return bookRepository.save(book);
        }).orElse(null);
    }

    // 5. Xóa sách theo ID
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}