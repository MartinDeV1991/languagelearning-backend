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

	@Autowired
	private WordService wordService;

	@Autowired
	private OpenAiApiService openAiApiService;

	@Autowired
	private RootWordService rootWordService;

	@GetMapping
	public List<Word> findAllWords() {
		return wordService.getAllWords();
	}

	@GetMapping("test")
	public List<String> test() {
		String DB = System.getenv("DB");
		String DB_USERNAME = System.getenv("DB_USERNAME");
		String DB_PASSWORD = System.getenv("DB_PASSWORD");
		String DB_HOST = System.getenv("DB_HOST");
		String DEEPL_API_KEY = System.getenv("DEEPL_API_KEY");
		String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
		return Arrays.asList(DB, DB_USERNAME, DB_PASSWORD, DB_HOST);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Word> findById(@PathVariable long id) {
		Optional<Word> optionalWord = wordService.findById(id);
	    return optionalWord.map(word -> ResponseEntity.ok(word))
	                       .orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/new")
	public ResponseEntity<?> addWord(@RequestBody Word word) {
		try {
			return ResponseEntity.ok(wordService.addWord(word));
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
	public ResponseEntity<RootWord> getRootWord(@PathVariable long id) {
		RootWord result = rootWordService.getRootWord(wordService.findById(id).get());
		return ResponseEntity.ok(result);
	}

}
