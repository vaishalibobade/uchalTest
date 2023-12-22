package com.uchal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.BlockRequestEntity;
import com.uchal.model.ApiException;
import com.uchal.repository.BlockRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BlockRequestService {

    private final BlockRequestRepository blockRequestRepository;

    @Autowired
    public BlockRequestService(BlockRequestRepository blockRequestRepository) {
        this.blockRequestRepository = blockRequestRepository;
    }

    public List<BlockRequestEntity> getAllBlockRequests() {
        return blockRequestRepository.findAll();
    }
    public List<BlockRequestEntity> getAllActiveBlockRequests() {
        return blockRequestRepository.findAllByStatus();
    }

    public Optional<BlockRequestEntity> getBlockRequestById(int requestId) {
        return blockRequestRepository.findById(requestId);
    }

    public BlockRequestEntity createBlockRequest(BlockRequestEntity blockRequest) {
        // You can perform additional logic/validation here if needed
    	 if(blockRequest.getUserToBlock()==0)
         {
  			throw new ApiException("Please enter user to Block", 403);

         }
    	 if(blockRequest.getRemark()==null)
    	 {
   			throw new ApiException("Please enter Remark", 404);
 
    	 }
        return blockRequestRepository.save(blockRequest);
    }

    public BlockRequestEntity updateBlockRequest(int requestId, BlockRequestEntity updatedBlockRequest) {
        // Check if the block request with the given ID exists
        if (blockRequestRepository.existsById(requestId)) {
            // You can perform additional logic/validation here if needed
            updatedBlockRequest.setRequestId(requestId);
            return blockRequestRepository.save(updatedBlockRequest);
        } else {
  			throw new ApiException("Given Request Id does not exists !!", 402);

            // Handle the case where the block request with the given ID does not exist
            // You can throw an exception, return a default value, or handle it as appropriate
        }
    }

    
    
    public boolean blockUser(int userId)
    {
    	 if (blockRequestRepository.getByUserToBlock(userId)==null)
    	 {
    		 return false;
    	 } 
    	 else{
    		 blockRequestRepository.updateRequestStatus(userId,"Blocked");
    		 
    	 }
		 return true;

    }
    
   
    
    public void deleteBlockRequest(int requestId) {
        blockRequestRepository.deleteById(requestId);
    }
}
