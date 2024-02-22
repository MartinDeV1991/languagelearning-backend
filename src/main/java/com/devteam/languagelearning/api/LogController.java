package com.devteam.languagelearning.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.languagelearning.model.Log;
import com.devteam.languagelearning.model.Statistics;
import com.devteam.languagelearning.service.LogService;
import com.devteam.languagelearning.service.StatisticsService;

@RestController
@RequestMapping("api/log")
public class LogController {

	@Autowired
	LogService logService;

	@GetMapping
	public List<Log> findAllLogs() {
		return logService.getAllLogs();
	}
		
	@PostMapping("/{user_id}")
	public Log addLog(@PathVariable long user_id, @RequestBody Log log) {
		return logService.addLog(user_id, log);
	}
}

