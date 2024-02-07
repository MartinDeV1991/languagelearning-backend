package com.devteam.languagelearning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    // @Value("${OPENAI_API_KEY}")
    // private static String openAiKey;

   private static String openAiKey = System.getenv("OPENAI_API_KEY");
//    private static final String openAiKey = "test";
    
    public static String getOpenAiKey() {
    	System.out.println("open: " + openAiKey);
        if (openAiKey == null || openAiKey.isEmpty()) {
    		openAiKey = "key";
    	}
        System.out.println("open: " + openAiKey);
        return openAiKey;
    }
}
