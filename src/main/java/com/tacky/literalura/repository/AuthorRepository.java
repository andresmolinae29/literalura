package com.tacky.literalura.repository;

import com.tacky.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.deathYear >= :year AND a.birthYear <= :year")
    List<Author> findByAuthorYear(int year);
}
