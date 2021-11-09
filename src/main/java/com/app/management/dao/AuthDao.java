package com.app.management.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.app.management.entity.UserEntity;
import com.app.management.entity.UserRolesEntity;

@Repository
public class AuthDao {

	@PersistenceContext
	private EntityManager em;

	public UserEntity validateUser(String username) {
		return this.em.find(UserEntity.class, username);
	}

	public void signup(UserEntity entity) {
		this.em.persist(entity);

	}

	public void saveRole(UserRolesEntity entity) {
		this.em.persist(entity);
	}

}
