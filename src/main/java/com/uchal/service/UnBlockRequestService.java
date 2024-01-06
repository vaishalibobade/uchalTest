package com.uchal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uchal.entity.UnBlockRequestEntity;
import com.uchal.model.ApiException;
import com.uchal.model.UnBlockRequestModel;
import com.uchal.repository.UnBlockRequestRepository;

@Service
public class UnBlockRequestService {
	
	
	
	private final UnBlockRequestRepository unBlockRequestRepository;

    @Autowired
    public UnBlockRequestService(UnBlockRequestRepository unBlockRequestRepository) {
        this.unBlockRequestRepository = unBlockRequestRepository;
    }

    public List<UnBlockRequestEntity> getAllUnBlockRequests() {
        return unBlockRequestRepository.findAll();
    }
    
    
    
    
    
    
    public List<UnBlockRequestModel> getAllActiveBlockRequests() {
        List<Object[]> resultList = unBlockRequestRepository.findAllByStatusWithUserDetails();

        List<UnBlockRequestModel> unBlockRequestModels = new ArrayList<>();

        for (Object[] result : resultList) {
            UnBlockRequestEntity unBlockRequestEntity = (UnBlockRequestEntity) result[0];
            String userToUnBlockName = (String) result[1]; // Assuming the userToBlockName is in the second position

            UnBlockRequestModel unBlockRequestModel = new UnBlockRequestModel(
            		unBlockRequestEntity.getRequestId(),
            		unBlockRequestEntity.getUserToUnBlock(),
                    userToUnBlockName,
                    unBlockRequestEntity.getRequestedBy(),
                    unBlockRequestEntity.getRemark(),
                    unBlockRequestEntity.getRequestStatus(),
                    unBlockRequestEntity.getCreatedBy(),
                    unBlockRequestEntity.getCreatedOn()
            );

            unBlockRequestModels.add(unBlockRequestModel);
        }

        return unBlockRequestModels;
    }
    
    
    
    
    public Optional<UnBlockRequestEntity> getUnBlockRequestById(int requestId) {
        return unBlockRequestRepository.findById(requestId);
    }

    public UnBlockRequestEntity createUnBlockRequest(UnBlockRequestEntity unBlockRequest) {
        // You can perform additional logic/validation here if needed
    	 if(unBlockRequest.getUserToUnBlock()==0)
         {
  			throw new ApiException("Please enter user to UnBlock", 403);

         }
    	 if(unBlockRequest.getRemark()==null)
    	 {
   			throw new ApiException("Please enter Remark", 404);
 
    	 }
    	 unBlockRequest.setRequestStatus("Active");
        return unBlockRequestRepository.save(unBlockRequest);
    }

    public UnBlockRequestEntity updateUnBlockRequest(int requestId, UnBlockRequestEntity updatedUnBlockRequest) {
        // Check if the block request with the given ID exists
        if (unBlockRequestRepository.existsById(requestId)) {
            // You can perform additional logic/validation here if needed
        	updatedUnBlockRequest.setRequestId(requestId);
            return unBlockRequestRepository.save(updatedUnBlockRequest);
        } else {
  			throw new ApiException("Given Request Id does not exists !!", 402);

            // Handle the case where the block request with the given ID does not exist
            // You can throw an exception, return a default value, or handle it as appropriate
        }
    }

    
   @Transactional 
    public boolean unBlockUser(int userId)
    {
    	 if (unBlockRequestRepository.getByUserToUnBlock(userId)==null)
    	 {
    		 return false;
    	 } 
    	 else{
    		 unBlockRequestRepository.updateRequestStatus(userId,"UnBlocked");
    		 
    	 }
		 return true;

    }
    
   
    
    public void deleteUnBlockRequest(int requestId) {
    	unBlockRequestRepository.deleteById(requestId);
    }

}
