package com.devteam.languagelearning.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.model.Word;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>{
    List<Word> findByRootWordId(@Param("rootWordId") long id);
    List<Word> findByUser(User user);
}
