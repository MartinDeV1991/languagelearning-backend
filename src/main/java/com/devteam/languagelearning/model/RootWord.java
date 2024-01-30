package com.devteam.languagelearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "root_word")
public class RootWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String word;

    private String language;

    private String partOfSpeech;

    private String definitionInEnglish;

    public RootWord(String partOfSpeech, String word) {
        this.partOfSpeech = partOfSpeech;
        this.word = word;
    }

    public RootWord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getDefinitionInEnglish() {
        return definitionInEnglish;
    }

    public void setDefinitionInEnglish(String definitionInEnglish) {
        this.definitionInEnglish = definitionInEnglish;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
