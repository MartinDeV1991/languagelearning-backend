package com.devteam.languagelearning.persistence;

import com.devteam.languagelearning.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}
