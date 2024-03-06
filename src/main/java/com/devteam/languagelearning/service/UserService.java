package com.devteam.languagelearning.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devteam.languagelearning.model.JwtTokenProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.persistence.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findById(long id) {
		return this.userRepository.findById(id);
	}

	public User update(User user) {
		return this.userRepository.save(user);
	}

	public User create(User user) {
		user.setCreationDate(LocalDate.now());
		this.userRepository.save(user);
		return user;
	}

	public Object[] login(User loginInfo) {
		Optional<User> optionalUser = this.userRepository.findByEmail(loginInfo.getEmail());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (loginInfo.getPassword().equals(user.getPassword())) {
				String secretKey = RandomStringUtils.random(100,true,true);
				String token = jwtTokenProvider.createToken(user.getId(), secretKey);
				System.out.println("JWT TOKEN");
				System.out.println(token);
				user.setToken(secretKey);
				userRepository.save(user);
				Object[] userInfo = new Object[2];
				userInfo[0] = user;
				userInfo[1] = token;
				return userInfo;
			}
		}
		return null;
	}
	
	public void deleteById(long id) {
		this.userRepository.deleteById(id);
	}
}
