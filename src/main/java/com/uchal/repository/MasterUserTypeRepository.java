package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.MasterUserType;
@Repository
public interface MasterUserTypeRepository  extends JpaRepository<MasterUserType, Integer> {

//    @Query("SELECT u FROM MasterUserType u WHERE u.userType = :userType")
	public MasterUserType findByAbreviation(String abreviation);
	
	
	@Query("SELECT u FROM MasterUserType u WHERE u.userType <> :userType")
	List<MasterUserType> findByUserTypeNotEqual(@Param("userType") String userType);

	
	
}
