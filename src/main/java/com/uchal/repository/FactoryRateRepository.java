package com.uchal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uchal.entity.FactoryRateEntity;

	@Repository
	public interface FactoryRateRepository extends JpaRepository<FactoryRateEntity, Integer> {
		

}
