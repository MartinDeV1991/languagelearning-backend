package com.devteam.languagelearning.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devteam.languagelearning.model.Word;

public interface WordRepository extends JpaRepository<Word, Long>{

}
