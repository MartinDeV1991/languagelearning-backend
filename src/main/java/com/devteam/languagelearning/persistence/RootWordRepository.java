package com.devteam.languagelearning.persistence;

import com.devteam.languagelearning.model.RootWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootWordRepository extends JpaRepository<RootWord, Long> {
}
