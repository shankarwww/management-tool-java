package com.app.management.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvironmentModel {
	private String environmentId;
	private String subscriberId;
	private int environmentSeq;
	private String actionType;
	private String url;
	private String createdBy;
	private LocalDateTime createdOn;
	private String updatedBy;
	private LocalDateTime updatedOn;
	private String publicKey;
	private boolean active;
}
