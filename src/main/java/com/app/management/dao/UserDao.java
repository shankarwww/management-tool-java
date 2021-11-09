package com.app.management.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.management.entity.UserEntity;

@Repository
public interface UserDao extends CrudRepository<UserEntity, String> {
	UserEntity findByUserId(String userId);
}
