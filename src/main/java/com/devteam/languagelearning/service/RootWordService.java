package com.devteam.languagelearning.service;

import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.persistence.RootWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RootWordService {
    @Autowired
    private RootWordRepository rootWordRepository;

    public RootWord addRootWord(String rootWord){
        RootWord newRootWord = new RootWord();
        newRootWord.setWord(rootWord);
        return rootWordRepository.save(newRootWord);
    }
}
