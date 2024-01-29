package com.devteam.languagelearning.api;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@DeleteMapping("/delete/{id}")
	public Word deleteWord(@PathVariable long id) {
		return wordService.deleteWord(id);
	}
}
