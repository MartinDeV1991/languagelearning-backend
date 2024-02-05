package com.devteam.languagelearning.config;

import com.deepl.api.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DeeplConfig {

	private String deeplApiKey;
	// @Value("${DEEPL_API_KEY}")
	// private String deeplApiKey;

	@Bean
	public Translator translator() {
		deeplApiKey = System.getenv("DEEPL_API_KEY");
		System.out.println("key1: " + deeplApiKey);

		if (deeplApiKey == null || deeplApiKey.isEmpty()) {
			deeplApiKey = "key";
		}
		return new Translator(deeplApiKey);
	}
}