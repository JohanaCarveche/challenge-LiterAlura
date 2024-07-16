package com.aluracursos.LiterAlura.repository;

import com.aluracursos.LiterAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
}
