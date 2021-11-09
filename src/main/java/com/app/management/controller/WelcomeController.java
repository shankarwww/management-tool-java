package com.app.management.controller;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class WelcomeController {

	@GetMapping("/greet")
	public String greet() {
		log.info("In greet mapping..");
		return "hello user..@" + LocalDateTime.now().toString();
	}

	@GetMapping("/greet-admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String greetAdmin() {
		log.info("In greet admin mapping..");
		return "hello admin..@" + LocalDateTime.now().toString();
	}

	@GetMapping("/greet-superadmin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String greetSuperAdmin() {
		log.info("In greet super admin mapping..");
		return "hello superadmin..@" + LocalDateTime.now().toString();
	}

}
