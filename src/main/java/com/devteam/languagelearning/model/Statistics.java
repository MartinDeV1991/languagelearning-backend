package com.devteam.languagelearning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistics")
public class Statistics {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@OneToOne(mappedBy = "statistics")
	private Word word;
	
	private long attempts;
	private long guessedCorrectly;
	private boolean flag;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	public long getAttempts() {
		return attempts;
	}
	public void setAttempts(long attempts) {
		this.attempts = attempts;
	}
	public long getGuessedCorrectly() {
		return guessedCorrectly;
	}
	public void setGuessedCorrectly(long guessedCorrectly) {
		this.guessedCorrectly = guessedCorrectly;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
		
}
