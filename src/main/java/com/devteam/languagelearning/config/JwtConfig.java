package com.devteam.languagelearning.config;

import com.devteam.languagelearning.model.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        long validityInMilliseconds = 3600000; // 1 hour
//        long validityInMilliseconds = 3000; // 3 seconds (for testing)
        return new JwtTokenProvider(validityInMilliseconds);
    }
}