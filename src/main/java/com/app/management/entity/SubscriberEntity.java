package com.app.management.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "subscribers")
@Data
public class SubscriberEntity {

	@Id
	@Column(name = "subscriber_id")
	private String subscriberId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "type")
	private String type;

	@Column(name = "description")
	private String description;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "active")
	private boolean active;

	@OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EnvironmentEntity> environments = new ArrayList<>();

}
