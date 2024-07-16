package com.aluracursos.LiterAlura.service;

import com.aluracursos.LiterAlura.model.Author;
import com.aluracursos.LiterAlura.model.Book;
import com.aluracursos.LiterAlura.repository.AuthorRepository;
import com.aluracursos.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final String API_URL = "https://gutendex.com/books/";

    public Book findBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            return book;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?search=" + title;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);

        if (response != null && !response.getResults().isEmpty()) {
            Book apiBook = response.getResults().get(0);
            for (Author author : apiBook.getAuthors()) {
                Author dbAuthor = authorRepository.findByName(author.getName());
                if (dbAuthor == null) {
                    dbAuthor = authorRepository.save(author);
                }
                apiBook.getAuthors().set(apiBook.getAuthors().indexOf(author), dbAuthor);
            }
            return bookRepository.save(apiBook);
        }

        return null;
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorsByYear(Integer year) {
        return authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
    }

    public List<Book> findBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    // Define BookResponse class to map the API response
    static class BookResponse {
        private int count;
        private String next;
        private String previous;
        private List<Book> results;

        // Getters and Setters
    }
}

