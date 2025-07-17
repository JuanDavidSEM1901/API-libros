package com.devsenior.jdruizped.library_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.jdruizped.library_management.model.dto.BookRequest;
import com.devsenior.jdruizped.library_management.model.dto.BookResponse;
import com.devsenior.jdruizped.library_management.service.bookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Libros", description = "Controlador para la gestión de libros en la biblioteca")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/books")
public class BookController {
  private final bookService bookService;

  @Operation(summary = "Listar todos los libros", description = "Obtiene una lista completa de todos los libros registrados.")
  @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(schema = @Schema(implementation = BookResponse.class)))
  @GetMapping
  public List<BookResponse> getAllBooks() {
    return bookService.getAllBooks();
  }

  @Operation(summary = "Obtener un libro por ID", description = "Devuelve los detalles de un libro dado su identificador único.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Libro encontrado", content = @Content(schema = @Schema(implementation = BookResponse.class))),
      @ApiResponse(responseCode = "404", description = "Libro no encontrado")
  })
  @GetMapping("{id}")
  public BookResponse getBookById(
      @Parameter(description = "ID del libro a buscar", example = "1") @PathVariable Long id) {
    return bookService.getById(id);
  }

  @Operation(summary = "Buscar libros por nombre", description = "Filtra los libros por nombre (palabra clave o título exacto).")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Libros encontrados con ese nombre", content = @Content(schema = @Schema(implementation = BookResponse.class))),
      @ApiResponse(responseCode = "400", description = "Parámetro inválido")
  })
  @GetMapping("search")
  public List<BookResponse> getBooksByName(
      @Parameter(description = "Nombre o palabra clave a buscar", example = "Cien años de soledad") @RequestParam(name = "name") String name) {
    return bookService.getByName(name);
  }

  @Operation(summary = "Registrar un nuevo libro", description = "Crea un nuevo registro de libro con la información proporcionada.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Libro creado exitosamente", content = @Content(schema = @Schema(implementation = BookResponse.class))),
      @ApiResponse(responseCode = "400", description = "Datos inválidos para el libro")
  })
  @PostMapping
  public BookResponse createBook(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo libro", required = true, content = @Content(schema = @Schema(implementation = BookRequest.class), examples = @ExampleObject(value = """
          {
            "title": "El amor en los tiempos del cólera",
          "author": "Gabriel García Márquez",
          "isbn": "12345678",
          "datePublication": "2025-08-19",
          "genre": "Novela",
          "state": "Disponible"
          }
          """))) @Valid @RequestBody BookRequest book) {
    return bookService.create(book);
  }

  @Operation(summary = "Actualizar un libro existente", description = "Modifica los datos de un libro existente, identificado por su ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente", content = @Content(schema = @Schema(implementation = BookResponse.class))),
      @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
      @ApiResponse(responseCode = "400", description = "Datos inválidos")
  })
  @PutMapping("{id}")
  public BookResponse updateBook(
      @Parameter(description = "ID del libro a actualizar", example = "5") @PathVariable("id") Long id,
      @Parameter(description = "Nuevos datos del libro") @Valid @RequestBody BookRequest book) {
    return bookService.update(id, book);
  }

  @Operation(summary = "Eliminar un libro", description = "Elimina el registro de un libro según su ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Libro no encontrado")
  })
  @DeleteMapping("{id}")
  public void deleteBook(
      @Parameter(description = "ID del libro a eliminar", example = "10") @PathVariable("id") Long id) {
    bookService.delete(id);
  }

  @Operation(summary = "Prestar un libro", description = "Cambia el estado de un libro a 'Prestado' si está disponible. Devuelve el libro actualizado.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Libro prestado exitosamente", content = @Content(schema = @Schema(implementation = BookResponse.class))),
      @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
      @ApiResponse(responseCode = "400", description = "El libro no está disponible para prestar")
  })
  @PostMapping("{id}/prestar")
  public BookResponse provideBook(
      @Parameter(description = "ID del libro a prestar", example = "3") @PathVariable Long id) {
    return bookService.provide(id);
  }

}
