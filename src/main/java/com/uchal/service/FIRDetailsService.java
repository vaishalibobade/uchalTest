package com.uchal.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.FIRDetails;
import com.uchal.repository.FIRDetailsRepository;
import com.uchal.repository.LoginDetailsRepository;
import com.uchal.repository.UserDetailsRepository;

@Service
public class FIRDetailsService {
	private final FIRDetailsRepository firDetailsRepository;
	private final LoginDetailsService loginDetailsService;
	@Autowired
	private EntityManager entityManager;

	public FIRDetailsService(FIRDetailsRepository firDetailsRepository, LoginDetailsService loginDetailsService) {
		this.firDetailsRepository = firDetailsRepository;
		this.loginDetailsService = loginDetailsService;
	}
	 public FIRDetails createFIRDetails(FIRDetails firDetails) {
	        return firDetailsRepository.save(firDetails);
	    }

	    public List<FIRDetails> getAllFIRDetails() {
	        return firDetailsRepository.findAll();
	    }
	    public List<FIRDetails> getAllFIRDetailsByID(int uploadedBy) {
	        return firDetailsRepository.findAllFIRDetailsByID(uploadedBy);
	    }
	    
	    

}
