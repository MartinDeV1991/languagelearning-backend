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

import com.devteam.languagelearning.model.Statistics;
import com.devteam.languagelearning.service.StatisticsService;

@RestController
@RequestMapping("api/statistics")
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;

	@GetMapping
	public List<Statistics> findAllStatistics() {
		return statisticsService.getAllStatistics();
	}
	
	@PostMapping("/{word_id}")
	public Statistics addStatistics(@PathVariable long word_id) {
		return statisticsService.addStatistics(word_id);
	}
	
	@PutMapping("/{statistics_id}")
	public Statistics changeStatistics(@PathVariable long statistics_id, @RequestBody Statistics input) {
		Optional<Statistics> optionalStatistics = statisticsService.findById(statistics_id);
		if (optionalStatistics.isPresent()) {
			Statistics statistics = optionalStatistics.get();
			
			return statisticsService.changeStatistics(statistics, input);
		} else {
			return null;
		}
	}

}
