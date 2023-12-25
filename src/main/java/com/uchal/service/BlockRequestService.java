package com.uchal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uchal.entity.BlockRequestEntity;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.BlockRequestModel;
import com.uchal.model.SearchUserOutputModel;
import com.uchal.repository.BlockRequestRepository;

import java.util.ArrayList;
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
    
    
    
    
    
    public List<BlockRequestModel> getAllActiveBlockRequests() {
        List<Object[]> resultList = blockRequestRepository.findAllByStatusWithUserDetails();

        List<BlockRequestModel> blockRequestModels = new ArrayList<>();

        for (Object[] result : resultList) {
            BlockRequestEntity blockRequestEntity = (BlockRequestEntity) result[0];
            String userToBlockName = (String) result[1]; // Assuming the userToBlockName is in the second position

            BlockRequestModel blockRequestModel = new BlockRequestModel(
                    blockRequestEntity.getRequestId(),
                    blockRequestEntity.getUserToBlock(),
                    userToBlockName,
                    blockRequestEntity.getRequestedBy(),
                    blockRequestEntity.getRemark(),
                    blockRequestEntity.getRequestStatus(),
                    blockRequestEntity.getCreatedBy(),
                    blockRequestEntity.getCreatedOn()
            );

            blockRequestModels.add(blockRequestModel);
        }

        return blockRequestModels;
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
    	 blockRequest.setRequestStatus("Active");
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

    
   @Transactional 
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
