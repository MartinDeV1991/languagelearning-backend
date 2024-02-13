package com.devteam.languagelearning.persistence;

import com.devteam.languagelearning.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>{
    List<Word> findByRootWordId(@Param("rootWordId") long id);
    List<Word> findByUser(User user);
    @Query("SELECT DISTINCT w.book FROM Word w WHERE w.user.id = :userId")
    List<Book> findBooksByUserId(@Param("userId") Long userId);
}
