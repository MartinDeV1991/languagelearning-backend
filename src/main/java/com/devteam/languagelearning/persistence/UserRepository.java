package com.devteam.languagelearning.persistence;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devteam.languagelearning.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
