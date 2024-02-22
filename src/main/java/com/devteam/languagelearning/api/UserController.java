package com.devteam.languagelearning.api;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devteam.languagelearning.model.User;
import com.devteam.languagelearning.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> findAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{user_id}")
	public User findUserById(@PathVariable long user_id) {
		Optional<User> optionalUser = userService.findById(user_id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user;
		}
		return null;
	}
	
	@PostMapping("login")
    public User login(@RequestBody User user) {
        return this.userService.login(user);
    }
	
	@PostMapping("signup")
    public User create(@RequestBody User user) {
        return this.userService.create(user);
    }
	
	@DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {
        this.userService.deleteById(id);
    }
	
}
