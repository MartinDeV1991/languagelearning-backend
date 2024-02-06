package com.devteam.languagelearning.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteam.languagelearning.model.Statistics;
import com.devteam.languagelearning.model.Word;

public interface StatisticsRepository extends JpaRepository<Statistics, Long>{
	Statistics findByWord(Word word);
}
