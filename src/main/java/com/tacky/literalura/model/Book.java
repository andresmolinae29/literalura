package com.tacky.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Language languages;
    private Integer downloadCount;
    @ManyToOne(
            cascade = CascadeType.DETACH
    )
    private Author author;

    public Book() {}

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.languages = Language.fromString(bookData.languages().get(0));
        this.downloadCount = bookData.downloadCount();
        this.author = new Author(bookData.authors().get(0));
    }

    public Book(BookData book, Author author) {
        this.title = book.title();
        this.languages = Language.fromString(book.languages().get(0));
        this.downloadCount = book.downloadCount();
        this.author = author;
    }

    @Override
    public String toString() {
        return "----------------------\n" +
                "Book: " + this.getTitle() + "\n" +
                "Language: " + this.getLanguages() + "\n" +
                "Downloads: " + this.getDownloadCount() + "\n" +
                "Author: " + this.author.getFirstName() + " " + this.author.getLastName() + "\n" +
                "----------------------\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguages() {
        return languages;
    }

    public void setLanguages(Language languages) {
        this.languages = languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthors() {
        return author;
    }

    public void setAuthors(Author author) {
        this.author = author;
    }
}
