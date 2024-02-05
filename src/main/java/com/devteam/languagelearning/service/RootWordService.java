package com.devteam.languagelearning.service;

import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.RootWordRepository;
import com.devteam.languagelearning.persistence.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RootWordService {
    @Autowired
    private RootWordRepository rootWordRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private OpenAiApiService openAiApiService;

    public RootWord addRootWord(String rootWord){
        RootWord newRootWord = new RootWord();
        newRootWord.setWord(rootWord);
        return rootWordRepository.save(newRootWord);
    }

    public RootWord setRootWord(Word word) {
        if (word.getRootWord() == null) {
            RootWord rootWord = getRootWord(word);
            Optional<RootWord> optional = rootWordRepository.findByWordAndPartOfSpeechAndLanguageIgnoreCase(rootWord.getWord(), rootWord.getPartOfSpeech(), word.getSourceLanguage());
            if (optional.isEmpty()) {
                openAiApiService.getRootWordDefinition(rootWord);
                rootWordRepository.save(rootWord);
            } else {
                rootWord = optional.get();
            }
            word.setRootWord(rootWord);
            wordRepository.save(word);
            return rootWord;
        }
        return word.getRootWord();
    }

    public RootWord getRootWord(Word word) {
        RootWord rootWord;
        String partOfSpeech = openAiApiService.getPartOfSpeech(word);
        if ("verb".equals(partOfSpeech)) {
            rootWord = openAiApiService.getRootVerb(word);
        }
        else if ("noun".equals(partOfSpeech)) {
            rootWord = openAiApiService.getRootNoun(word);
        }
        else {
            rootWord = openAiApiService.getRoot(word, partOfSpeech);
        }
        rootWord.setLanguage(word.getSourceLanguage());
        rootWord.setPartOfSpeech(partOfSpeech);
        return rootWord;
    }

}
