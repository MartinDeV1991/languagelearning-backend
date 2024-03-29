package com.devteam.languagelearning.api;

import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.RootWordRepository;
import com.devteam.languagelearning.persistence.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@RequestMapping("api/root-word")
public class RootWordController {

    @Autowired
    RootWordRepository rootWordRepository;

    @Autowired
    WordRepository wordRepository;

    @GetMapping
    public List<RootWord> getRootWords() {
        return rootWordRepository.findAll();
    }

    @GetMapping("{id}/words")
    public List<Word> getWordsOfRootWord(@PathVariable long id) {
        return wordRepository.findByRootWordId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRootWord(@PathVariable long id) {
        wordRepository.findByRootWordId(id).forEach((word) -> word.setRootWord(null));
        rootWordRepository.delete(rootWordRepository.findById(id).get());
    }
}
