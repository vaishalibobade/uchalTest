package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.UnBlockRequestEntity;

@Repository
public interface UnBlockRequestRepository extends JpaRepository<UnBlockRequestEntity, Integer> {

	
	@Query("SELECT a FROM UnBlockRequestEntity a WHERE  a.userToUnBlock =:userToUnBlock and a.requestStatus ='Active'")
	List<UnBlockRequestEntity> getByUserToUnBlock(@Param("userToUnBlock") int userToUnBlock);
	
	@Modifying
    @Query("UPDATE UnBlockRequestEntity b SET b.requestStatus = :newStatus WHERE b.userToUnBlock = :userToUnBlock")
    void updateRequestStatus(@Param("userToUnBlock") int userToUnBlock, @Param("newStatus") String newStatus);
	
	@Query("SELECT a, CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName) AS userToUnBlockName " +
	        "FROM UnBlockRequestEntity a " +
	        "JOIN UserDetails u ON a.userToUnBlock = u.userId " +
	        "WHERE a.requestStatus = 'Active'")
	List<Object[]> findAllByStatusWithUserDetails();
	
}
