package com.aluracursos.LiterAlura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    // Getters and Setters
}
