package com.tacky.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String fullname;
    private String fisrtName;
    private String lastName;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {}

    public Author(AuthorData author) {

        this.fullname = author.name();
        this.fisrtName = author.name().split(", ")[1];
        this.lastName = author.name().split(", ")[0];
        this.birthYear = author.birthYear();
        this.deathYear = author.deathYear();
    }

    @Override
    public String toString() {
        return "----------------------\n" +
                "First name: " + this.getFisrtName() + "\n" +
                "Last name: " + this.getLastName() + "\n" +
                "BirthYear: " + this.getBirthYear() + '\n' +
                "DeathYear: " + this.getDeathYear() + "\n" +
        "----------------------\n" ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        books.forEach(b -> b.setAuthors(this));
        this.books = books;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
