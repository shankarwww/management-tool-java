package com.app.management.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.management.dao.AuthDao;
import com.app.management.dao.UserDao;
import com.app.management.entity.UserEntity;
import com.app.management.entity.UserRolesEntity;
import com.app.management.model.UserModel;
import com.app.management.model.UserRoleModel;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthDao authDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public void delete(String id) {
		this.userDao.deleteById(id);
	}

	public UserModel findById(String id) {
		UserEntity entity = this.userDao.findById(id).orElse(new UserEntity());
		return buildModel(entity);
	}

	public boolean save(UserModel model) {
		UserEntity entity = buildEntity(model);
		boolean isPresent = this.userDao.existsById(model.getUserId());
		if (isPresent) {
			throw new RuntimeException("UserId already registered: " + model.getUserId());
		}
		this.userDao.save(entity);
		return true;
	}

	private UserEntity buildEntity(UserModel model) {
		UserEntity entity = new UserEntity();

		entity.setUserId(model.getUserId());
		entity.setPassword(this.bcryptEncoder.encode(model.getPassword()));
		entity.setFullName(model.getFullName());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUserRoles(buildRoles(model.getUserId()));

		return entity;
	}

	private List<UserRolesEntity> buildRoles(String userId) {
		UserRolesEntity rolesEntity = new UserRolesEntity();
		rolesEntity.setCreatedOn(LocalDateTime.now());
		rolesEntity.getPk().setRole("ADMIN");
		rolesEntity.getPk().setUserId(userId);
		return Arrays.asList(rolesEntity);
	}

	private UserModel buildModel(UserEntity entity) {
		UserModel model = new UserModel();

		model.setUserId(entity.getUserId());
		model.setFullName(entity.getFullName());
		model.setCreatedOn(entity.getCreatedOn().toString());

		return model;
	}

	public void assignRole(UserRoleModel role) {
		UserRolesEntity entity = new UserRolesEntity();
		entity.getPk().setUserId(role.getEmailId());
		entity.getPk().setRole(role.getRole());
		entity.setCreatedOn(LocalDateTime.now());
		this.authDao.saveRole(entity);
	}

}
