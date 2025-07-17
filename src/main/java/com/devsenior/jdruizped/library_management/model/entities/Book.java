package com.devsenior.jdruizped.library_management.model.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "author", nullable = false)
  private String author;

  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @Column(name = "datePublication", nullable = false)
  private LocalDate datePublication;

  @Column(name = "genre")
  private String genre;

  @Column(name = "state", nullable = false)
  private String state;

}