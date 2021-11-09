package com.app.management.security.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.model.AuthTokenModel;
import com.app.management.model.UserModel;
import com.app.management.model.UserRoleModel;
import com.app.management.security.config.TokenProvider;
import com.app.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/token")
	public ResponseEntity<AuthTokenModel> getToken(@RequestBody UserModel model) throws AuthenticationException {

		log.info("In getToken with user {}", model.toString());

		final Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						model.getUserId(),
						model.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = this.tokenProvider.generateToken(authentication);
		log.info("the token is {}", token);
		return ResponseEntity.ok(new AuthTokenModel(token));
	}

	@PostMapping(value = "/assignrole")
	public Map<String, Boolean> assignRole(@RequestBody UserRoleModel role) {
		this.userService.assignRole(role);
		return Collections.singletonMap("status", true);
	}

}
