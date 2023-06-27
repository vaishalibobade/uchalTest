package com.uchal.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.EmpVendorAssociation;
import com.uchal.mapper.EmpVendorAssociationMapper;
import com.uchal.model.ApiException;
import com.uchal.model.EmpVendorAssociationModel;
import com.uchal.repository.EmpVendorAssociationRepository;
@Service
public class EmpVendorAssociationService {
	  private final EmpVendorAssociationRepository empVendorAssociationRepository;
		private static final Logger logger = LogManager.getLogger(LoginController.class);

	    @Autowired
	    public EmpVendorAssociationService(EmpVendorAssociationRepository empVendorAssociationRepository) {
	        this.empVendorAssociationRepository = empVendorAssociationRepository;
//	        this.empVendorAssociationMapper=empVendorAssociationMapper;
	    }
	    public EmpVendorAssociation saveEmpVendorAssociationDetails (EmpVendorAssociation empVendorAssociationDetails)
	    {
	    	return empVendorAssociationRepository.save(empVendorAssociationDetails);
	    	 
	    }
	    
	    
	    public EmpVendorAssociationModel savePaymentDetails (EmpVendorAssociationModel empVendorAssociationModel)
	    {
	    	EmpVendorAssociationMapper empVendorAssociationMapper=new EmpVendorAssociationMapper();
	    	EmpVendorAssociation empVendorAssociation = empVendorAssociationRepository.save(empVendorAssociationMapper.mapToEntity(empVendorAssociationModel));
	    
	    	
	    	
	    	if (empVendorAssociation ==null)
	    	{
	    		throw new ApiException("Error while saving Details", 404);
	    	}
    		return empVendorAssociationMapper.mapToModel(empVendorAssociation);

	    	
	    }
	    
	    
	    
}
