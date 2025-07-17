package com.devsenior.jdruizped.library_management.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Objeto que representa la solicitud para crear o actualizar un libro")
public class BookRequest {

  @NotBlank(message = "el campo title es OBLIGATORIO")
  @Size(min = 2, message = "El campo title debe tener minimo 2 caracteres")
  @Schema(description = "Titulo del libro", example = "Cien Años De Soledad", required = true, minLength = 2)
  private String title;

  @NotBlank(message = "el campo autor es OBLIGATORIO")
  @Size(min = 2, max = 25, message = "El campo autor debe tener minimo 2 caracteres y maximo 20")
  @Schema(description = "Nombre del autor", example = "Gabriel Garcia Marquez", required = true, minLength = 2, maxLength = 20)
  private String author;

  @NotNull(message = "el campo ISBN es obligatorio")
  @Size(min = 4, max = 10, message = "el ISBN debe tener entre 4 y 10 caracteres")
  @Schema(description = "ISBN unico del libro", example = "789896", required = true, minLength = 4, maxLength = 10)
  private String isbn;

  @NotNull(message = "El campo datePublication es obligatorio")
  @Schema(description = "Fecha de publicacion del libro en formato yyyy-MM-dd", example = "2025-08-19", required = true)
  private LocalDate datePublication;

  @NotBlank(message = "El campo genre es obligatorio")
  @Size(min = 3, max = 12, message = "El campo genre debe tener mínimo 3 caracteres y maximo 12")
  @Schema(description = "genero del libro", example = "Critico", required = false, minLength = 3, maxLength = 12)
  private String genre;

  @NotBlank(message = "El campo state es obligatorio")
  @Size(min = 3, max = 10, message = "El campo state debe tener mínimo 3 caracteres y máximo 10")
  @Pattern(regexp = "Disponible|Prestado", message = "El estado debe ser 'Disponible' o 'Prestado'")
  @Schema(description = "Estado del libro: Disponible o Prestado", example = "Disponible", required = true, allowableValues = {
      "Disponible", "Prestado" }, minLength = 3, maxLength = 10)
  private String state;
}
