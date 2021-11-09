package com.app.management.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "environments")
@Data
public class EnvironmentEntity {

	@EmbeddedId
	private EnvironmentEntityPk pk = new EnvironmentEntityPk();

	@Column(name = "url")
	private String url;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "public_key")
	private String publicKey;

	@Column(name = "active")
	private boolean active;

	@Column(name = "auth")
	private boolean auth;

	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriber_id", referencedColumnName = "subscriber_id", insertable = false, updatable = false)
	private SubscriberEntity subscriber;

	@Embeddable
	@Data
	public static class EnvironmentEntityPk implements Serializable {

		private static final long serialVersionUID = 5391118571796766733L;

		@Column(name = "environment_id")
		private String environmentId;

		@Column(name = "subscriber_id")
		private String subscriberId;

		@Column(name = "environment_seq")
		private int environmentSeq;
	}

}
