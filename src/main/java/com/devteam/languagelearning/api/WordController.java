package com.devteam.languagelearning.api;

import java.util.Arrays;
import java.util.List;

import java.util.Optional;

import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.service.OpenAiApiService;
import com.devteam.languagelearning.service.RootWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.service.WordService;

@RestController
@RequestMapping("api/word")
public class WordController {

	// Constructor injection instead of field injection (https://medium.com/@detoxicdev/field-injection-v-s-constructor-injection-dd9db2d85b7b)
	private final WordService wordService;
	private final RootWordService rootWordService;
	public WordController(WordService wordService, RootWordService rootWordService) {
		this.wordService = wordService;
		this.rootWordService = rootWordService;
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

	@PostMapping("/new/for_user/{user_id}")
	public ResponseEntity<?> addWord(@RequestBody Word word, @PathVariable long user_id) {
		try {
			return ResponseEntity.ok(wordService.addWord(word, user_id));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deepl failed. Check that the request body contains 'word', 'contextSentence', and 'translatedTo'. You may also include 'sourceLanguage'.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public Word deleteWord(@PathVariable long id) {
		return wordService.deleteWord(id);
	}

	@GetMapping("/root/{id}")
	public ResponseEntity<?> getRootWord(@PathVariable long id) {
		try {
			Optional<Word> word = wordService.findById(id);
			if (word.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The word with ID " + id + " could not be found.");
			}
			RootWord result = rootWordService.setRootWord(word.get());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editWord(@PathVariable long id, @RequestBody Word word) {
		Word newWord = wordService.editWord(id, word);
		return ResponseEntity.ok(newWord);
	}
}
