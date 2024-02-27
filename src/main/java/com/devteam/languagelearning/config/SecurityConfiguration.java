package com.devteam.languagelearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devteam.languagelearning.filter.AuthorizationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

	@Autowired
    private AuthorizationFilter authorizationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authorize) -> authorize.requestMatchers("api/**", "api/user/login", "api/user/signup").permitAll().anyRequest().authenticated())
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(withDefaults()).csrf((csrf) -> csrf.disable());
		return http.build();
	}

}