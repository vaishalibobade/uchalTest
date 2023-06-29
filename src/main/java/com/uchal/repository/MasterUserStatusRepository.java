package com.uchal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uchal.entity.MasterUserStatus;

public interface MasterUserStatusRepository extends JpaRepository<MasterUserStatus, Integer>  {

	MasterUserStatus save(MasterUserStatus masterUserStatus);

}
