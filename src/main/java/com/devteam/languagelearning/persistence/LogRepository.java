package com.devteam.languagelearning.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteam.languagelearning.model.Log;

public interface LogRepository extends JpaRepository<Log, Long>{
}
