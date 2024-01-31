package com.devteam.languagelearning.persistence;

import com.devteam.languagelearning.model.RootWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RootWordRepository extends JpaRepository<RootWord, Long> {
    Optional<RootWord> findByWordAndPartOfSpeechAndLanguageIgnoreCase(String word, String partOfSpeech, String language);
}
