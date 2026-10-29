package com.nguyentrananhquoc.library_backend.repository;

import com.nguyentrananhquoc.library_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}