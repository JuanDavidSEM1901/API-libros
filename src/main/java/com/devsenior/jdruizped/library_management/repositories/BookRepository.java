package com.devsenior.jdruizped.library_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsenior.jdruizped.library_management.model.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
  @Query("SELECT b FROM Book b WHERE lower(b.title) like %:name%")
  List<Book> getAllBooksByName(@Param("name") String name);
}
