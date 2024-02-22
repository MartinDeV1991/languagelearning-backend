package com.devteam.languagelearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.model.Log;
import com.devteam.languagelearning.persistence.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private UserService userService;

	public List<Log> getAllLogs() {
		return logRepository.findAll();
	}

	public Optional<Log> findById(long id) {
		return this.logRepository.findById(id);
	}

	public Log addLog(long user_id, Log newLog) {
		Optional<User> optionalUser = userService.findById(user_id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
				newLog.setUser(user);
				return logRepository.save(newLog);
		}
		return null;
	}	

}
