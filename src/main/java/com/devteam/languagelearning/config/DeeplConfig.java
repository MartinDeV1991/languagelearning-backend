package com.devteam.languagelearning.config;

import com.deepl.api.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DeeplConfig {

    // @Value("${DEEPL_API_KEY}")
    // private String deeplApiKey2;

    private String deeplApiKey;
    // private String deeplApiKey = "test";

    @Bean
    public Translator translator() {
        deeplApiKey = System.getenv("DEEPL_API_KEY");
    	System.out.println("key1: " + deeplApiKey);
        // System.out.println("key2: " + deeplApiKey2);
    	if (deeplApiKey == null || deeplApiKey.isEmpty()) {
    		deeplApiKey = "key";
    	}
    	System.out.println("key1: " + deeplApiKey);
        return new Translator(deeplApiKey);
    }
}
