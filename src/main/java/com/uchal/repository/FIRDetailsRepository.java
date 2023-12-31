package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.FIRDetails;
import com.uchal.entity.UserDetails;

@Repository
public interface FIRDetailsRepository extends JpaRepository<FIRDetails, Integer> {

	@Query("SELECT u FROM FIRDetails u WHERE u.createdBy = :createdBy ")
	List<FIRDetails> findAllFIRDetailsByID(@Param("createdBy") int createdBy);
//
//	@Query("SELECT u.firImage FROM FIRDetails u WHERE u.employeeId = :employeeId ")
//	List<Object[]> findFIRDetailsByemployeeId(@Param("employeeId") int employeeId);
	
	
	
	 @Query("SELECT u.firImage FROM FIRDetails u WHERE u.employeeId = :employeeId")
	    List<byte[]> findFIRImagesByEmployeeId(@Param("employeeId") int employeeId);
	}


