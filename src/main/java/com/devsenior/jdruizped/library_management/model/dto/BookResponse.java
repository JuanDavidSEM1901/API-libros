package com.devsenior.jdruizped.library_management.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto de respuesta que contiene los datos del libro")
public class BookResponse {

  @Schema(description = "identificador unico del libro", example = "1")
  private Long id;
  @Schema(description = "titulo del libro", example = "Cien Años De Soledad")
  private String title;
  @Schema(description = "nombre del autor", example = "Gabriel Garcia Marquez")
  private String author;
  @Schema(description = "ISBN del libro", example = "78907654")
  private String isbn;
  @Schema(description = "Fecha de publicacion del libro en formato yyyy-MM-dd", example = "2025-03-22")
  private LocalDate datePublication;

  @Schema(description = "genero del libro", example = "Critico")
  private String genre;
  @Schema(description = "Estado del libro", example = "Disponible")
  private String state;
}
