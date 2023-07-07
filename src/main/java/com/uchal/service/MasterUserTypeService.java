package com.uchal.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.MasterUserType;
import com.uchal.repository.MasterUserTypeRepository;

@Service
public class MasterUserTypeService {

    @Autowired
    private final MasterUserTypeRepository masterUserTypeRepository;
    @Autowired
    public MasterUserTypeService(MasterUserTypeRepository masterUserTypeRepository) {
        this.masterUserTypeRepository = masterUserTypeRepository;
    }
    
    public MasterUserType saveMasterUserType(MasterUserType masterUserType) {
        return masterUserTypeRepository.save(masterUserType);
    }

    public MasterUserType getMasterUserTypeById(int id) {
        return masterUserTypeRepository.findById(id).orElse(null);
    }

    
    public List<MasterUserType> getAllMasterUserTypes() {
        return masterUserTypeRepository.findAll();
    }

    
    public void deleteMasterUserType(int id) {
        masterUserTypeRepository.deleteById(id);
    }
    

public boolean isAuthorisedCRUD(String type, String  loggedType)
{
	System.out.println(type);
	System.out.println(loggedType);
//	int id =masterUserTypeRepository.findByAbreviation(type)
	int loggedId=masterUserTypeRepository.findByAbreviation(loggedType).getId();
	System.out.println(masterUserTypeRepository.findByAbreviation(type).getUserBelongTo());
	System.out.println(masterUserTypeRepository.findByAbreviation(type).getId());

	System.out.println(masterUserTypeRepository.findByAbreviation(type).getUserType());

//	System.out.println(getMasterUserTypeById(id).getUserType());
	List<String> belongsTo=Arrays.asList(masterUserTypeRepository.findByAbreviation(type).getUserBelongTo().split(","));
	if (belongsTo.contains(loggedId))
	{
		return true;
	}
	return false;
	
}





}

