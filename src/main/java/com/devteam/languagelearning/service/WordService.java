package com.devteam.languagelearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import com.devteam.languagelearning.model.Book;
import com.devteam.languagelearning.model.Statistics;
import com.devteam.languagelearning.persistence.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.WordRepository;

@Service
public class WordService {

	private final WordRepository wordRepository;
	private final RootWordService rootWordService;
	private final UserService userService;
	private final BookService bookService;
	private final Translator translator;

	@Autowired
	public WordService(Translator translator, RootWordService rootWordService, UserService userService, BookService bookService, WordRepository wordRepository) {
		this.translator = translator;
		this.rootWordService = rootWordService;
		this.userService = userService;
		this.bookService = bookService;
		this.wordRepository = wordRepository;
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
		Word newWord = new Word(word.getWord(), word.getContextSentence(), word.getSourceLanguage(), word.getTranslatedTo());
		// translate both word and sentence with deepl, save to the word object
		TextResult translatedWord = translator.translateText(word.getWord(), word.getSourceLanguage(),
				word.getTranslatedTo());
		TextResult translatedSentence = translator.translateText(word.getContextSentence(), word.getSourceLanguage(),
				word.getTranslatedTo());
		newWord.setTranslation(translatedWord.getText());
		newWord.setTranslatedContextSentence(translatedSentence.getText());

		// set source language from API response if not already provided
		if (word.getSourceLanguage() == null) {
			word.setSourceLanguage(translatedSentence.getDetectedSourceLanguage());
		} else {
			newWord.setSourceLanguage(word.getSourceLanguage());
		}

		// Set user
		Optional<User> optionalUser = userService.findById(user_id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			newWord.setUser(user);
		}

		// set root word (either existing or create new one)
		rootWordService.determineAndSetRootWord(newWord);

		// Set book
		Book book = bookService.getOrCreateBook(word.getBook());
		newWord.setBook(book);
		book.getWords().add(newWord);

		// save and return new word object
		return newWord;
	}

	public Word editWord(long id, Word newWord) {
		Optional<Word> optionalWord = this.findById(id);
		if (optionalWord.isPresent()) {
			Word word = optionalWord.get();
	        word.setWord(newWord.getWord());
	        word.setSourceLanguage(newWord.getSourceLanguage());
	        word.setTranslatedTo(newWord.getTranslatedTo());
	        word.setContextSentence(newWord.getContextSentence());
	        word.setTranslatedContextSentence(newWord.getTranslatedContextSentence());
	        word.setTranslation(newWord.getTranslation());
	        word.setRootWord(newWord.getRootWord());
		    word.setBook(bookService.getOrCreateBook(newWord.getBook()));
	        return wordRepository.save(word);
		} else {
			throw new NoSuchElementException();
		}
	}

	public Word deleteWord(long id) throws NoSuchElementException {
		Word word = wordRepository.findById(id).orElseThrow();
		wordRepository.deleteById(id);
		return word;
	}

	public ArrayList<Word> deleteWords(ArrayList<Long> ids) {
		ArrayList<Word> deletedWords = new ArrayList<>();
		for (Long id : ids) {
			try {
				Word word = wordRepository.findById(id).orElseThrow();
				wordRepository.deleteById(id);
				deletedWords.add(word);
				System.out.println("Word with id " + id + " deleted.");
			} catch (NoSuchElementException e) {
				System.out.println("Word with id " + id + " not found.");
			}
		}
		return deletedWords;
	}
}
