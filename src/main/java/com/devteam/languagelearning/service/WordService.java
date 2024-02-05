package com.devteam.languagelearning.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.WordRepository;

@Service
public class WordService {
	
	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private RootWordService rootWordService;

	@Autowired
	private UserService userService;
	
	private final Translator translator;

	@Autowired
	public WordService(Translator translator, RootWordService rootWordService) {
		this.translator = translator;
		this.rootWordService = rootWordService;
	}
	public List<Word> getAllWords() {
		return wordRepository.findAll();
	}

	public Optional<Word> findById(long id) {
		return this.wordRepository.findById(id);
	}

	public List<Word> getWordsByUser(long user_id) {
		Optional<User> optionalUser = userService.findById(user_id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
		
		return this.wordRepository.findByUser(user);
		} else {
			return null;
		}
	}
	
	public Word addWord(Word word, long user_id) throws DeepLException, InterruptedException {
				// translate both word and sentence with deepl, save to the word object
                TextResult translatedWord = translator.translateText(word.getWord(), word.getSourceLanguage(), word.getTranslatedTo());
				TextResult translatedSentence = translator.translateText(word.getContextSentence(), word.getSourceLanguage(), word.getTranslatedTo());
				word.setTranslation(translatedWord.getText());
				word.setTranslatedContextSentence(translatedSentence.getText());

				// set source language from API response if not already provided
				if (word.getSourceLanguage() == null) {
					word.setSourceLanguage(translatedSentence.getDetectedSourceLanguage());
				}
				// set root word (either existing or create new one)
				rootWordService.determineAndSetRootWord(word);
				
				Optional<User> optionalUser = userService.findById(user_id);
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					word.setUser(user);
				}
				
				// save and return new word object
				return this.wordRepository.save(word);
	}

	public Word editWord(long id, Word editedWord) {
		return wordRepository.save(editedWord);
	}

	public Word deleteWord(long id) throws NoSuchElementException {
		Word word = wordRepository.findById(id).orElseThrow();
		wordRepository.deleteById(id);
		return word;
	}
}
