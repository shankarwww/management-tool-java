package com.app.management.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SubscriberModel {
	private String subscriberId;
	private String userId;
	private String type;
	private String description;
	private LocalDateTime createdOn;
	private String updatedBy;
	private LocalDateTime updatedOn;
	private boolean active;
	private List<EnvironmentModel> environments = new ArrayList<>();
}
