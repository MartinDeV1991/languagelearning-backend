package com.devteam.languagelearning.api;

import java.util.List;

import java.util.Optional;
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

	@GetMapping
	public List<Word> findAllWords() {
		return wordService.getAllWords();
	}
//	
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
}
