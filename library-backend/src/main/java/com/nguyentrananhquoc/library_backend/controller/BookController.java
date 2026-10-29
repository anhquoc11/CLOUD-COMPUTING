package com.nguyentrananhquoc.library_backend.controller;

import com.nguyentrananhquoc.library_backend.entity.Book;
import com.nguyentrananhquoc.library_backend.service.BookService;
import com.nguyentrananhquoc.library_backend.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*") // Hỗ trợ CORS cho Frontend trên S3
@Tag(name = "Book Controller", description = "Các API quản lý sách và tích hợp lưu trữ ảnh trên AWS S3")
public class BookController {
    private final BookService bookService;
    private final S3Service s3Service;

    public BookController(BookService bookService, S3Service s3Service) {
        this.bookService = bookService;
        this.s3Service = s3Service;
    }

    @Operation(summary = "Lấy danh sách tất cả các cuốn sách")
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(summary = "Lấy thông tin chi tiết một cuốn sách theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Thêm một cuốn sách mới")
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Upload ảnh bìa sách lên AWS S3")
    @PostMapping("/upload-cover")
    public ResponseEntity<Map<String, String>> uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Lỗi upload ảnh lên S3: " + e.getMessage()));
        }
    }

    @Operation(summary = "Cập nhật thông tin cuốn sách theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updated = bookService.updateBook(id, bookDetails);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Xóa cuốn sách theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Đã xóa sách thành công"));
        }
        return ResponseEntity.notFound().build();
    }
}