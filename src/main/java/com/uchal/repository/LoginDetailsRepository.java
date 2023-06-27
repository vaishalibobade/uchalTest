package com.uchal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uchal.entity.LoginDetails;


public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Integer> {
    // You can add custom query methods or use the default methods provided by JpaRepository
	public LoginDetails findByUsername(String username);
    Optional<LoginDetails> findByUserDetailsUserId(int userId);
	public Optional<LoginDetails> findAllByUsername(String username);
	
}