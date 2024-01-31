package com.devteam.languagelearning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

//    @Value("${openai.api.key}")
//    private static String openAiKey;

    private static final String openAiKey = System.getenv("OPENAI_API_KEY");
//    private static final String openAiKey = "test";
    
    public static String getOpenAiKey() {
    	System.out.println("key1234: " + openAiKey);
        return openAiKey;
    }
}