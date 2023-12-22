package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.BlockRequestEntity;

@Repository
public interface BlockRequestRepository extends JpaRepository<BlockRequestEntity, Integer> {
	

	@Query("SELECT a FROM BlockRequestEntity a WHERE  a.requestStatus ='Active'")
	 List<BlockRequestEntity> findAllByStatus();	
	
	@Query("SELECT a FROM BlockRequestEntity a WHERE  a.userToBlock =:userToBlock and a.requestStatus ='Active'")
	List<BlockRequestEntity> getByUserToBlock(@Param("userToBlock") int userToBlock);
	
	@Modifying
    @Query("UPDATE BlockRequestEntity b SET b.requestStatus = :newStatus WHERE b.userToBlock = :userToBlock")
    void updateRequestStatus(@Param("userToBlock") int userToBlock, @Param("newStatus") String newStatus);
	
}
