package com.uchal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uchal.entity.MasterPaymentStatus;

public interface MasterPaymentStatusRepository extends JpaRepository<MasterPaymentStatus, Integer> { 
	

}
