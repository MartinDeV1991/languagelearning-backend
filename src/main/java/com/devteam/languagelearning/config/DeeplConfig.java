package com.devteam.languagelearning.config;

import com.deepl.api.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DeeplConfig {

//    @Value("${deepl.api.key}")
//    private String deeplApiKey;

    private final String deeplApiKey = System.getenv("DEEPL_API_KEY");
//    private final String deeplApiKey = "test";

    @Bean
    public Translator translator() {
        return new Translator(deeplApiKey);
    }
}