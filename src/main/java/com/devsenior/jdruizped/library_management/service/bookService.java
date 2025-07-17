package com.devsenior.jdruizped.library_management.service;

import java.util.List;

import com.devsenior.jdruizped.library_management.model.dto.BookRequest;
import com.devsenior.jdruizped.library_management.model.dto.BookResponse;

public interface bookService {
  List<BookResponse> getAllBooks();

  BookResponse getById(Long id);

  List<BookResponse> getByName(String name);

  BookResponse create(BookRequest book);

  BookResponse update(Long id, BookRequest bookUpdate);

  void delete(Long id);

  BookResponse provide(Long id);
}
