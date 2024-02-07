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

	public Statistics findByWord(long word_id) {
		Optional<Word> optionalWord = wordService.findById(word_id);
		if (optionalWord.isPresent()) {
			Word word = optionalWord.get();
			Statistics statistics = this.statisticsRepository.findByWord(word);
			if (statistics == null) {
				statistics = createStatistics(word_id);
			}
			return statistics;
		}
		return null;
	}

	public Statistics createStatistics(long word_id) {
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

	public Statistics addAttempts(Statistics statistics, boolean correct) {
		statistics.setAttempts(statistics.getAttempts()+1);
		if (correct) {
			statistics.setGuessedCorrectly(statistics.getGuessedCorrectly() + 1);
		}
		return statisticsRepository.save(statistics);
	}
	
	public Statistics setFlag(Statistics statistics, boolean flag) {
		statistics.setAttempts(statistics.getAttempts()+1);
		if (flag) {
			statistics.setFlag(true);
		} else {
			statistics.setFlag(false);
		}
		return statisticsRepository.save(statistics);
	}
	

	public Statistics changeStatistics(Statistics statistics, Statistics input) {
		statistics.setAttempts(input.getAttempts());
		statistics.setGuessedCorrectly(input.getGuessedCorrectly());
		return statisticsRepository.save(statistics);
	}

}
