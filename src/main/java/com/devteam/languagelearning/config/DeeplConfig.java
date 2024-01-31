package com.devteam.languagelearning.config;

import com.deepl.api.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DeeplConfig {

    // @Value("${DEEPL_API_KEY}")
    // private String deeplApiKey;

   // private String deeplApiKey = System.getenv("DEEPL_API_KEY");;
   private final String deeplApiKey = "test";

    @Bean
    public Translator translator() {
    	System.out.println("key: " + deeplApiKey);
    	if (deeplApiKey == null || deeplApiKey.isEmpty()) {
    		deeplApiKey = "key";
    	}
        return new Translator(deeplApiKey);
    }
}
