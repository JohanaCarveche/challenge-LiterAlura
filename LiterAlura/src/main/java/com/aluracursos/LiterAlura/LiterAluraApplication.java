package com.aluracursos.LiterAlura;

import com.aluracursos.LiterAlura.model.Book;
import com.aluracursos.LiterAlura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Seleccione una opción:");
			System.out.println("1. Buscar libro por título");
			System.out.println("2. Listar libros registrados");
			System.out.println("3. Listar autores registrados");
			System.out.println("4. Listar autores vivos en un determinado año");
			System.out.println("5. Listar libros por idioma");
			System.out.println("6. Salir");

			int option = scanner.nextInt();
			scanner.nextLine();  // Consume newline

			switch (option) {
				case 1:
					System.out.println("Ingrese el título del libro:");
					String title = scanner.nextLine();
					Book book = bookService.findBookByTitle(title);
					if (book != null) {
						System.out.println("Título: " + book.getTitle());
						System.out.println("Autor: " + book.getAuthors().get(0).getName());
						System.out.println("Idioma: " + book.getLanguage());
						System.out.println("Número de descargas: " + book.getDownloadCount());
					} else {
						System.out.println("Libro no encontrado.");
					}
					break;
				case 2:
					bookService.listBooks().forEach(b -> System.out.println(b.getTitle()));
					break;
				case 3:
					bookService.listAuthors().forEach(a -> System.out.println(a.getName()));
					break;
				case 4:
					System.out.println("Ingrese el año:");
					int year = scanner.nextInt();
					bookService.findAuthorsByYear(year).forEach(a -> System.out.println(a.getName()));
					break;
				case 5:
					System.out.println("Ingrese el idioma (es, en, fr, pt):");
					String language = scanner.nextLine();
					bookService.findBooksByLanguage(language).forEach(b -> System.out.println(b.getTitle()));
					break;
				case 6:
					System.out.println("Saliendo...");
					return;
				default:
					System.out.println("Opción no válida.");
					break;
			}
		}
	}
}
