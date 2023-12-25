package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uchal.entity.MasterUserStatus;

public interface MasterUserStatusRepository extends JpaRepository<MasterUserStatus, Integer>  {

	MasterUserStatus save(MasterUserStatus masterUserStatus);
	
	@Query("SELECT u FROM MasterUserStatus u WHERE u.id NOT IN :idList")
	List<MasterUserStatus> findAllExcpetBlock(@Param("idList") List<Integer> idList);


}
