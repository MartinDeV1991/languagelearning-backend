package com.devteam.languagelearning.config;

import org.springframework.beans.factory.annotation.Value;

public class OpenAiConfig {

//    @Value("${openai.api.key}")
//    private static String openAiKey;

    private static final String openAiKey = System.getenv("OPENAI_API_KEY");

    public static String getOpenAiKey() {
        return openAiKey;
    }
}