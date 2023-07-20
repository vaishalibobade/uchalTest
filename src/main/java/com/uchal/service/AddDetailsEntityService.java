package com.uchal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.AddDetailsEntity;
import com.uchal.repository.AddDetailsEntityRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddDetailsEntityService {
    
    @Autowired
    private AddDetailsEntityRepository addDetailsEntityRepository;

    public AddDetailsEntity  save(AddDetailsEntity addDetailsEntity) {
    	
        return addDetailsEntityRepository.save(addDetailsEntity);
    }

    public AddDetailsEntity findById(int id) {
        return addDetailsEntityRepository.findById(id).orElse(null);
    }

    public List<AddDetailsEntity> findAll() {
        return addDetailsEntityRepository.findAll();
    }

    public void delete(AddDetailsEntity addDetailsEntity) {
        addDetailsEntityRepository.delete(addDetailsEntity);
    }
    
    
    public AddDetailsEntity saveAddDetails (AddDetailsEntity details)
    {
    	details.setCreatedAt(LocalDateTime.now());
    	AddDetailsEntity savedDetails=save(details);
    	return savedDetails;
    	
    	
    }
    
    
    
    public List<AddDetailsEntity> getAvailableAdds()
    {
//    	LocalDate currentDate =LocalDate.now();
    	
    	 return addDetailsEntityRepository.getAllAvailableAdds();
    	
    	
    	
    }
    
}

