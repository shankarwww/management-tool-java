package com.app.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.model.UserModel;
import com.app.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	// @Secured("ROLE_USER")
	// @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/app/{id}")
	public UserModel getOne(@PathVariable(value = "id") String id) {
		return this.userService.findById(id);
	}

	@PostMapping(value = "/signup")
	public boolean saveUser(@RequestBody UserModel model) {
		log.info("Going to register user with data {}", model);
		return this.userService.save(model);
	}

}
