package com.devteam.languagelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.WordRepository;

@Service
public class WordService {
	
	@Autowired
	private WordRepository wordRepository;

	public List<Word> getAllWords() {
		return wordRepository.findAll();
	}

	public Optional<Word> findById(long id) {
		return this.wordRepository.findById(id);
	}
	
}
