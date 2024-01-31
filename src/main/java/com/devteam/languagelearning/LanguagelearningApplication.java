package com.devteam.languagelearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class LanguagelearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguagelearningApplication.class, args);
	}

}
