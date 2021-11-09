package com.app.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.management.dao.ApplicationManagementDao;
import com.app.management.entity.EnvironmentEntity;
import com.app.management.entity.EnvironmentTypeEntity;
import com.app.management.entity.SubscriberEntity;
import com.app.management.model.EnvironmentModel;
import com.app.management.model.EnvironmentTypeModel;
import com.app.management.model.SubscriberModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ApplicationManagementService {

	@Autowired
	private ApplicationManagementDao dao;

	public List<SubscriberModel> getAllApplication(String userId) {

		List<SubscriberEntity> applications = null;
		boolean isSuperAdmin = this.dao.isSuperAdmin(userId);

		if (isSuperAdmin) {
			applications = this.dao.getAllApplications();
		} else {
			applications = this.dao.getUserApplications(userId);
		}

		List<SubscriberModel> models = new ArrayList<>();

		for (SubscriberEntity entity : applications) {
			SubscriberModel model = buildApplicationModel(entity);
			models.add(model);
			log.info("SubscriberModel added to list is {}", model);
		}

		log.info("The count of all subscribers is {}", models.size());
		return models;
	}

	public String createApplication(SubscriberModel model) {

		String contact = model.getUserId();

		boolean isPresent = this.dao.isUserPresent(contact);

		if (!isPresent) {
			throw new RuntimeException("User does not exit in the system");
		}

		SubscriberEntity entity = new SubscriberEntity();

		entity.setSubscriberId(model.getSubscriberId());
		entity.setUserId(model.getUserId());
		entity.setType(model.getType());
		entity.setDescription(model.getDescription());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setActive(true);

		this.dao.saveApplications(entity);

		log.info("Subscriber created with id {}", model.getSubscriberId());

		return model.getSubscriberId();
	}

	public String createEnvironment(EnvironmentModel model) {

		EnvironmentEntity entity = new EnvironmentEntity();

		entity.getPk().setSubscriberId(model.getSubscriberId());
		entity.getPk().setEnvironmentId(model.getEnvironmentId());
		entity.getPk().setEnvironmentSeq(model.getEnvironmentSeq());
		entity.setUrl(model.getUrl());
		entity.setCreatedBy(model.getCreatedBy());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setPublicKey(model.getPublicKey());
		entity.setActive(true);
		entity.setAuth(true);

		this.dao.saveEnvironment(entity);

		return "success";
	}

	public SubscriberModel getApplicationByName(String name) {
		SubscriberEntity entity = new SubscriberEntity();
		return buildApplicationModel(entity);
	}

	private SubscriberModel buildApplicationModel(SubscriberEntity entity) {
		SubscriberModel model = new SubscriberModel();

		model.setSubscriberId(entity.getSubscriberId());
		model.setUserId(entity.getUserId());
		model.setDescription(entity.getDescription());
		model.setType(entity.getType());
		model.setCreatedOn(entity.getCreatedOn());
		model.setUpdatedOn(entity.getUpdatedOn());
		model.setUpdatedBy(entity.getUpdatedBy());
		model.setActive(entity.isActive());

		for (EnvironmentEntity environment : entity.getEnvironments()) {
			EnvironmentModel envModel = new EnvironmentModel();
			envModel.setSubscriberId(environment.getPk().getSubscriberId());
			envModel.setEnvironmentId(environment.getPk().getEnvironmentId());
			envModel.setEnvironmentSeq(environment.getPk().getEnvironmentSeq());
			envModel.setUrl(environment.getUrl());
			envModel.setCreatedBy(environment.getCreatedBy());
			envModel.setCreatedOn(environment.getCreatedOn());
			envModel.setUpdatedBy(environment.getUpdatedBy());
			envModel.setUpdatedOn(environment.getUpdatedOn());
			envModel.setPublicKey(environment.getPublicKey());
			envModel.setActive(environment.isActive());

			model.getEnvironments().add(envModel);
		}

		return model;
	}

	public List<EnvironmentModel> getAllEnvironments(String userId, String subscriberId) {
		List<EnvironmentEntity> entityList = this.dao.getAllEnvironments(userId, subscriberId);

		List<EnvironmentModel> modelList = new ArrayList<>();

		for (EnvironmentEntity environment : entityList) {

			EnvironmentModel envModel = new EnvironmentModel();
			envModel.setSubscriberId(environment.getPk().getSubscriberId());
			envModel.setEnvironmentId(environment.getPk().getEnvironmentId());
			envModel.setEnvironmentSeq(environment.getPk().getEnvironmentSeq());
			envModel.setUrl(environment.getUrl());
			envModel.setCreatedBy(environment.getCreatedBy());
			envModel.setCreatedOn(environment.getCreatedOn());
			envModel.setUpdatedBy(environment.getUpdatedBy());
			envModel.setUpdatedOn(environment.getUpdatedOn());
			envModel.setPublicKey(environment.getPublicKey());
			envModel.setActive(environment.isActive());

			modelList.add(envModel);
		}
		log.info("Count of environment for the subscriberId {} is {}", subscriberId, modelList.size());
		return modelList;
	}

	public List<EnvironmentTypeModel> getEnvironmentTypes() {
		return this.dao.getEnvironmentTypes()
		.stream()
		.map(entity -> new EnvironmentTypeModel(entity.getType(), entity.getDescription()))
		.collect(Collectors.toList());
		
	}

}
