package com.app.management.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.model.EnvironmentModel;
import com.app.management.model.EnvironmentTypeModel;
import com.app.management.model.SubscriberModel;
import com.app.management.service.ApplicationManagementService;

@RestController
@RequestMapping("/api")
public class ApplicationManagementController {

	private static final String APP_ID = "appId";
	@Autowired
	private ApplicationManagementService service;

	@GetMapping("/application/{name}")
	public SubscriberModel getApplications(@PathVariable String name) {
		return this.service.getApplicationByName(name);
	}

	@GetMapping("/applications")
	public List<SubscriberModel> getApplicationsByUser(@RequestParam String userId) {
		return this.service.getAllApplication(userId);
	}

	@PostMapping("/createapplication")
	public Map<String, String> createApplication(@RequestBody SubscriberModel model) {
		String id = this.service.createApplication(model);
		return Collections.singletonMap(APP_ID, id);
	}

	@GetMapping("/allenvironments")
	public List<EnvironmentModel> getAllEnvironments(@RequestParam String userId, @RequestParam String subscriberId) {
		return this.service.getAllEnvironments(userId, subscriberId);
	}

	@PostMapping("/createenvironment")
	public Map<String, String> createEnvironment(@RequestBody EnvironmentModel environments) {
		String id = this.service.createEnvironment(environments);
		return Collections.singletonMap(APP_ID, id);
	}
	
	@GetMapping("/environmenttypes")
	public List<EnvironmentTypeModel> getEnvironmentTypes() {
		return this.service.getEnvironmentTypes();
	}
}
