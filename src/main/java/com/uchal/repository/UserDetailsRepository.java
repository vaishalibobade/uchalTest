package com.uchal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	// You can add custom query methods or use the default methods provided by
	// JpaRepository
	// Example: findByFirstName(String firstName);
    List<UserDetails> findAllByAdharNumber(long adharNumber);
    List<UserDetails> findAllByMobileNumber(long adharNumber);
    
    @Query("SELECT u FROM UserDetails u WHERE u.userId != :userId AND u.adharNumber = :adhar")
    List<UserDetails> findByAdharIdNotEqual(@Param("userId") int id, @Param("adhar") long adhar);

    
    @Query("SELECT u FROM UserDetails u WHERE u.userId != :userId AND u.mobileNumber= :mobile")
    List<UserDetails> findByMobileIdNotEqual(@Param("userId") int id,@Param("mobile") long mobile);
//	UserDetails findByUsername(String username);
	UserDetails findByFirstName(String username);
    

}