package com.devteam.languagelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.Statistics;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.StatisticsRepository;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsRepository statisticsRepository;

	@Autowired
	private WordService wordService;

	public List<Statistics> getAllStatistics() {
		return statisticsRepository.findAll();
	}

	public Optional<Statistics> findById(long id) {
		return this.statisticsRepository.findById(id);
	}

	public Statistics addStatistics(long word_id) {
		Optional<Word> optionalWord = wordService.findById(word_id);
		if (optionalWord.isPresent()) {
			Word word = optionalWord.get();
			if (word.getStatistics() == null) {
				Statistics statistics = new Statistics();
				word.setStatistics(statistics);
				return statisticsRepository.save(statistics);
			}
		}
		return null;
	}

	public Statistics changeStatistics(Statistics statistics, Statistics input) {
		statistics.setAttempts(input.getAttempts());
		statistics.setGuessedCorrectly(input.getGuessedCorrectly());
		return statisticsRepository.save(statistics);
	}

}
