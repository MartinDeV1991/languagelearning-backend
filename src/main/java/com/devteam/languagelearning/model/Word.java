package com.devteam.languagelearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "word")
public class Word {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String word;
	private String sourceLanguage;
	private String translatedTo;
	private String contextSentence;
	private String translatedContextSentence;
	private String translation;
	@ManyToOne
	@JoinColumn(name="root_word_id")
	private RootWord rootWord;
	
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
	public String getSourceLanguage() {
		return sourceLanguage;
	}
	public void setSourceLanguage(String sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}
	public String getTranslatedTo() {
		return translatedTo;
	}
	public void setTranslatedTo(String translatedTo) {
		this.translatedTo = translatedTo;
	}
	public String getContextSentence() {
		return contextSentence;
	}
	public void setContextSentence(String contextSentence) {
		this.contextSentence = contextSentence;
	}
	public String getTranslatedContextSentence() {
		return translatedContextSentence;
	}
	public void setTranslatedContextSentence(String translatedContextSentence) {
		this.translatedContextSentence = translatedContextSentence;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public RootWord getRootWord() {
		return rootWord;
	}

	public void setRootWord(RootWord rootWord) {
		this.rootWord = rootWord;
	}
}
