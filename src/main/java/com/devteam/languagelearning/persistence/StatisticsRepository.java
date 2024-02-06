package com.devteam.languagelearning.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteam.languagelearning.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long>{

}
