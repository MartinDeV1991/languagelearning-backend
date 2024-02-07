package com.devteam.languagelearning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "word")
public class Word {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Word cannot be blank")
	private String word;
	@Size(max = 5, message = "Use the ISO 639-1 code, e.g. 'NL', 'ES', 'FR'.")
	private String sourceLanguage;
	private String translatedTo;
	@Size(max = 500, message = "Context sentence cannot exceed 500 characters")
	private String contextSentence;
	private String translatedContextSentence;
	private String translation;
	
	@ManyToOne
	@JoinColumn(name="root_word_id")
	private RootWord rootWord;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "statistics_id")
	private Statistics statistics;
	
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Statistics getStatistics() {
		return statistics;
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	
}
