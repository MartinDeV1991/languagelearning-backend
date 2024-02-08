package com.devteam.languagelearning.api;

import java.util.*;

import com.deepl.api.DeepLException;
import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.service.OpenAiApiService;
import com.devteam.languagelearning.service.RootWordService;
import com.devteam.languagelearning.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.service.WordService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/word")
public class WordController {

	// Constructor injection instead of field injection (https://medium.com/@detoxicdev/field-injection-v-s-constructor-injection-dd9db2d85b7b)
	private final WordService wordService;
	private final RootWordService rootWordService;
	private final StatisticsService statisticsService;
	public WordController(WordService wordService, RootWordService rootWordService, StatisticsService statisticsService) {
		this.wordService = wordService;
		this.rootWordService = rootWordService;
		this.statisticsService = statisticsService;
	}

	@GetMapping
	public List<Word> findAllWords() {
		return wordService.getAllWords();
	}
	
	@GetMapping("user/{user_id}")
	public List<Word> findWordsByUser(@PathVariable long user_id) {
		return wordService.getWordsByUser(user_id);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Word> findById(@PathVariable long id) {
		Optional<Word> optionalWord = wordService.findById(id);
	    return optionalWord.map(word -> ResponseEntity.ok(word))
	                       .orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/user/{user_id}")
	public ResponseEntity<?> addWord(@Valid @RequestBody Word word, @PathVariable long user_id) {
		try {
			Word newWord = wordService.addWord(word, user_id);
			statisticsService.createStatistics(newWord.getId());
			return ResponseEntity.ok(newWord);
		}
		catch (DeepLException | InterruptedException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deepl failed. Check that the request body contains 'word', 'contextSentence', and 'translatedTo'. You may also include 'sourceLanguage'.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteWord(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(wordService.deleteWord(id));
		}
		catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body("No element exists with that ID.");
		}
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteWords(@RequestBody ArrayList<Long> ids) {
		ArrayList<Word> deletedWords = wordService.deleteWords(ids);
		if (deletedWords.size() == ids.size()) {
			return ResponseEntity.status(200).body(deletedWords);
		} else if (deletedWords.isEmpty()) {
			return ResponseEntity.status(404).body("Failed to delete any words.");
		} else {
			return ResponseEntity.status(207).body(deletedWords);
		}
	}

	@PutMapping("/{id}/root")
	public ResponseEntity<?> setRootWord(@PathVariable long id) {
		try {
			Optional<Word> word = wordService.findById(id);
			if (word.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The word with ID " + id + " could not be found.");
			}
			RootWord result = rootWordService.determineAndSetRootWord(word.get());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editWord(@PathVariable long id, @Valid @RequestBody Word word) {
		Word newWord = wordService.editWord(id, word);
		return ResponseEntity.ok(newWord);
	}
}
