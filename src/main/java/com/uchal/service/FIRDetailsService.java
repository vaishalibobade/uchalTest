package com.uchal.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.entity.FIRDetails;
import com.uchal.model.FIRDetailsModel;
import com.uchal.model.PaymentDetailsModel;
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
	     
//	    public List<FIRDetailsModel> getFIRDetailsByUserId(int employeeId) {
//	    	 return setFIRDetailsModel(firDetailsRepository.findFIRDetailsByemployeeId(employeeId));
//	    }
//	    
	    
	    
	    public List<byte[]> getFIRImagesByEmployeeId(int employeeId) {
	    	
	    	
	    	
	        return firDetailsRepository.findFIRImagesByEmployeeId(employeeId);
	    }
	    
	    
	    
//	    public List<FIRDetailsModel> getFIRDetailsByUserId(int employeeId) {
////			 return userDetailsService.getAllEmployeeDetailsWithAssociation(loggedId);
//			List<Object[]> result = firDetailsRepository.findFIRDetailsByemployeeId(employeeId);
//			List<FIRDetailsModel> models = new ArrayList<>();
//			for (Object[] objects : result) {
////			     UserDetails userDetails = (UserDetails) objects[0];
////			     EmpVendorAssociation empVendorAssociation = (EmpVendorAssociation) objects[1];
//				FIRDetailsModel records = new FIRDetailsModel();
//				records.setFirImage((byte[]) objects[0]);
//
////				records.setName((String) objects[1]+" "+(String) objects[2]+" "+(String) objects[3]);
////				records.setMobileNumber((Long) objects[4]);
////				records.setUserType((String) objects[5]);
//				models.add(records);
//			}
//			return models;
//
//		}
	    
	    
	    
	    
	    public List<FIRDetails> getAllFIRDetailsByID(int uploadedBy) {
	        return firDetailsRepository.findAllFIRDetailsByID(uploadedBy);
	    }
	    
	    

}
