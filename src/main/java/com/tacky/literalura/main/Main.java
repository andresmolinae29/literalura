package com.tacky.literalura.main;

import com.tacky.literalura.model.*;
import com.tacky.literalura.repository.AuthorRepository;
import com.tacky.literalura.repository.BookRepository;
import com.tacky.literalura.service.ApiProcess;
import com.tacky.literalura.service.DataConvert;
import jdk.jfr.Category;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiProcess apiProcess = new ApiProcess();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConvert convert = new DataConvert();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private List<Book> books;
    private List<Author> authors;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void menu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    Elija la opcion a traves del numero:
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en determinado annio
                    5. Listar libros por idioma
                    0. Salir
                    
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitlle();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    listAuthors();
                    break;
                case 4:
                    listAuthorsByYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private BookData getBookData() {
        System.out.println("Nombre del libro a buscar: ");
        var bookName = scanner.nextLine();
        var url = URL_BASE + "?search=" + URLEncoder.encode(bookName.toLowerCase(), StandardCharsets.UTF_8);
        var json = apiProcess.getData(url);
        var result = convert.convert(json, DataResult.class);
        try {
            return result.results().get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("El libro buscado no se encuentra");
            return null;
        }
    }

    private void saveBook(Book book) {
        try {
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException e) {
            System.out.println("Ya existe en la base de datos");
        }
    }

    private void searchBookByTitlle() {
        BookData data = getBookData();

        if (data != null) {
            Optional<Author> author = authorRepository.findByFullname(data.authors().get(0).name());

            if (author.isPresent()) {
                Book book = new Book(data, author.get());
                saveBook(book);

            } else {
                Book book = new Book(data);
                authorRepository.save(book.getAuthors());
                saveBook(book);
            }
        }
    }

    private void listBooks() {
        books = bookRepository.findAll();
        books.stream()
                .limit(10)
                .forEach(System.out::println);
    }

    private void listAuthors() {
        authors = authorRepository.findAll();
        authors.stream()
                .limit(5)
                .forEach(System.out::println);
    }

    private void listAuthorsByYear() {
        System.out.println("Digite el anio para los autores vivos: ");
        var year = scanner.nextInt();
        scanner.nextLine();

        authors = authorRepository.findByAuthorYear(year);

        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados");
        }
        else {
            authors.stream()
                    .limit(5)
                    .forEach(System.out::println);
        }
    }

    private void listBooksByLanguage() {
        System.out.println("""
                    Elija la opcion a traves del numero:
                        1. Ingles
                        2. Frances
                        3. Espanol
                    """);

        var option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                listAuthorsByLanguage(Language.fromEspanol("Ingles"));
                break;
            case 2:
                listAuthorsByLanguage(Language.fromEspanol("Frances"));
                break;
            case 3:
                listAuthorsByLanguage(Language.fromEspanol("Espanol"));
                break;
            default:
                System.out.println("Opcion no valida");

        }
    }

    private void listAuthorsByLanguage(Language language) {
        books = bookRepository.findByLanguages(language);
        books.stream()
                .limit(5)
                .forEach(System.out::println);
    }
}

