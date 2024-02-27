package com.devteam.languagelearning.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizeHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println("token: " + authorizeHeader);
		if (authorizeHeader != null) {
			String parameter = request.getHeader("userId");
			try {
				long userId = Long.parseLong(parameter);
				if (validateToken(authorizeHeader, userId)) {
					System.out.println("Je krijgt permissie voor de request");
					setAuthentication(userId);
					filterChain.doFilter(request, response);
					return;
				}
			} catch (NumberFormatException e) {
			}
		}
		filterChain.doFilter(request, response);
		return;
	}

	private boolean validateToken(String token, long userId) {
		Optional<User> optionalUser = userService.findById(userId);
		if (optionalUser.isPresent()) {
			System.out.println("Gebruiker gevonden!!!");
			User user = optionalUser.get();
			return token.equals(user.getToken());
		} else {
			return false;
		}
	}

	private void setAuthentication(long userId) {
		Optional<User> optionalUser = userService.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

}