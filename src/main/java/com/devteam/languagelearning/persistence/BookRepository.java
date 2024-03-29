package com.devteam.languagelearning.persistence;

import com.devteam.languagelearning.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitleAndAuthorAndLanguage(String title, String author, String language);
}
