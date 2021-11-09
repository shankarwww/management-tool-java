package com.app.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "environment_type")
@Data
public class EnvironmentTypeEntity {

	@Id
	@Column(name = "type")
	private String type;

	@Column(name = "description")
	private String description;
}
