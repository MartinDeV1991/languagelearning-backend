package com.devteam.languagelearning.config;

import com.deepl.api.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeeplConfig {
    @Bean
    public Translator translator() {
        String apiKey = System.getenv("DEEPL_API_KEY");
        return new Translator(apiKey);
    }
}