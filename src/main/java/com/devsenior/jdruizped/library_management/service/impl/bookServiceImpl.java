package com.devsenior.jdruizped.library_management.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsenior.jdruizped.library_management.exception.BookListEmptyException;
import com.devsenior.jdruizped.library_management.exception.BookLoanedException;
import com.devsenior.jdruizped.library_management.exception.BookNotFoundException;
import com.devsenior.jdruizped.library_management.exception.NameNullOrEmptyException;
import com.devsenior.jdruizped.library_management.model.dto.BookRequest;
import com.devsenior.jdruizped.library_management.model.dto.BookResponse;
import com.devsenior.jdruizped.library_management.model.entities.Book;
import com.devsenior.jdruizped.library_management.repositories.BookRepository;
import com.devsenior.jdruizped.library_management.service.bookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class bookServiceImpl implements bookService {

  private final BookRepository bookRepository;

  @Override
  public List<BookResponse> getAllBooks() {
    var books = bookRepository.findAll().stream().map(this::toResponse).toList();
    if (books.isEmpty()) {
      throw new BookListEmptyException("Lista de libros vacia. Asegurese de ingresar por lo menos un libro.");
    }
    return books;
  }

  @Override
  public BookResponse getById(Long id) {
    return bookRepository.findById(id).map(this::toResponse)
        .orElseThrow(() -> new BookNotFoundException("No se ha encontrado el libro con ID: " + id));
  }

  @Override
  public List<BookResponse> getByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new NameNullOrEmptyException("El parámetro 'name' no puede estar vacío.");
    }
    List<Book> books = bookRepository.getAllBooksByName('%' + name + '%');

    if (books.isEmpty()) {
      throw new BookNotFoundException("No se encontraron libros con el nombre: " + name);
    }

    return books.stream().map(this::toResponse).toList();
  }

  @Override
  public BookResponse create(BookRequest book) {
    var entity = toEntity(book);

    var newBook = bookRepository.save(entity);

    return toResponse(newBook);
  }

  @Override
  public BookResponse update(Long id, BookRequest bookUpdate) {
    var entityOpional = bookRepository.findById(id);

    if (!entityOpional.isPresent()) {
      throw new BookNotFoundException("No se ha encontrado el libro con ID: " + id);
    }

    var entity = toEntity(bookUpdate);
    entity.setId(entityOpional.get().getId());

    var updatedEntity = bookRepository.save(entity);

    return toResponse(updatedEntity);
  }

  @Override
  public void delete(Long id) {
    bookRepository.deleteById(id);
  }

  private BookResponse toResponse(Book book) {
    var response = new BookResponse();
    response.setTitle(book.getTitle());
    response.setId(book.getId());
    response.setAuthor(book.getAuthor());
    response.setIsbn(book.getIsbn());
    response.setDatePublication(book.getDatePublication());
    response.setGenre(book.getGenre());
    response.setState(book.getState());
    return response;
  }

  private Book toEntity(BookRequest book) {
    var entity = new Book();

    entity.setTitle(book.getTitle());
    entity.setAuthor(book.getAuthor());
    entity.setIsbn(book.getIsbn());
    entity.setDatePublication(book.getDatePublication());
    entity.setGenre(book.getGenre());
    entity.setState(book.getState());

    return entity;
  }

  @Override
  public BookResponse provide(Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new BookNotFoundException("El libro con ID: " + id + " no se fue encontrado"));

    if (!book.getState().equalsIgnoreCase("Disponible")) {
      throw new BookLoanedException("El libro no esta disponible para prestar.");
    }

    book.setState("Prestado");
    Book bupdated = bookRepository.save(book);

    return toResponse(bupdated);

  }

}
