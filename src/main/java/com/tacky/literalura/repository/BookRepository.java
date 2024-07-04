package com.tacky.literalura.repository;

import com.tacky.literalura.model.Book;
import com.tacky.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguages(Language language);
}
