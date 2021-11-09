package com.app.management.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.app.management.entity.EnvironmentEntity;
import com.app.management.entity.EnvironmentTypeEntity;
import com.app.management.entity.SubscriberEntity;

@Repository
public class ApplicationManagementDao {

	@PersistenceContext
	private EntityManager em;

	public List<SubscriberEntity> getAllApplications() {
		return this.em
				.createQuery("from SubscriberEntity", SubscriberEntity.class)
				.getResultList();
	}

	public void saveApplications(SubscriberEntity entity) {
		this.em.persist(entity);
	}

	public void updateApplications(SubscriberEntity entity) {
		this.em.merge(entity);
	}

	public void saveEnvironment(EnvironmentEntity environmentEntity) {
		this.em.persist(environmentEntity);

	}

	public void updateEnvironment(EnvironmentEntity environmentEntity) {
		this.em.merge(environmentEntity);

	}

	public List<SubscriberEntity> getUserApplications(String userId) {
		return this.em
				.createQuery("from SubscriberEntity where userId= : userId", SubscriberEntity.class)
				.setParameter("userId", userId)
				.getResultList();

	}

	public boolean isUserPresent(String contact) {
		Long count = this.em.createQuery("select count(*) from UserEntity where userId = :userId", Long.class)
				.setParameter("userId", contact)
				.getSingleResult();

		return count > 0;
	}

	public List<EnvironmentEntity> getAllEnvironments(String userId, String subscriberId) {
		return this.em
				.createQuery("from EnvironmentEntity where pk.subscriberId = :subscriberId", EnvironmentEntity.class)
				.setParameter("subscriberId", subscriberId)
				.getResultList();
	}

	public boolean isSuperAdmin(String userId) {
		Long count = this.em.createQuery("select count(*) from UserRolesEntity where pk.userId = :userId and pk.role = :role", Long.class)
				.setParameter("userId", userId)
				.setParameter("role", "SUPER_ADMIN")
				.getSingleResult();

		return count > 0;
	}

	public List<EnvironmentTypeEntity> getEnvironmentTypes() {
		return em.createQuery("from EnvironmentTypeEntity", EnvironmentTypeEntity.class).getResultList();
		
	}

}
