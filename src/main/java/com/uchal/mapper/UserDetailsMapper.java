package com.uchal.mapper;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
//import org.springframework.mock.web.MockMultipartFile;


import com.uchal.entity.UserDetails;
import com.uchal.model.UserDetailsModel;

public class UserDetailsMapper {
    @SuppressWarnings("deprecation")
	public UserDetails mapToEntity(UserDetailsModel userDetailsModel) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userDetailsModel.getUserId());
        userDetails.setFirstName(userDetailsModel.getFirstName());
        userDetails.setLastName(userDetailsModel.getLastName());
        userDetails.setMiddleName(userDetailsModel.getMiddleName());
        userDetails.setCity(userDetailsModel.getCity());
        userDetails.setState(userDetailsModel.getState());
        userDetails.setCountry(userDetailsModel.getCountry());
        userDetails.setMobileNumber(userDetailsModel.getMobileNumber());
        userDetails.setAdharNumber(userDetailsModel.getAdharNumber());
        userDetails.setAdharImage((userDetailsModel.getAdharImage()));
        userDetails.setBloodgroup(userDetailsModel.getBloodgroup());
        userDetails.setCreatedBy(userDetailsModel.getCreatedBy());
     //   userDetails.setCreatedOn(new Date(userDetailsModel.getCreatedOn()));
        userDetails.setStreetDetail(userDetailsModel.getStreetDetail());
        userDetails.setUpdatedBy(userDetailsModel.getUpdatedBy());
       // userDetails.setUpdatedOn(new Date(userDetailsModel.getUpdatedOn()));
        userDetails.setUserType(userDetailsModel.getUserType());
        userDetails.setCurrentStatusId(userDetailsModel.getCurrentStatusId());
        
        
        
        
        
        // Map other fields as needed

        return userDetails;
    }

    public UserDetailsModel mapToModel(UserDetails userDetails) {
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setUserId(userDetails.getUserId());
        userDetailsModel.setFirstName(userDetails.getFirstName());
        userDetailsModel.setLastName(userDetails.getLastName());
        userDetailsModel.setMiddleName(userDetails.getMiddleName());
        userDetailsModel.setCity(userDetails.getCity());
        userDetailsModel.setState(userDetails.getState());
        userDetailsModel.setCountry(userDetails.getCountry());
        userDetailsModel.setMobileNumber(userDetails.getMobileNumber());
        userDetailsModel.setAdharNumber(userDetails.getAdharNumber());
         userDetailsModel.setAdharImage(userDetails.getAdharImage());
        userDetailsModel.setBloodgroup(userDetails.getBloodgroup());
        userDetailsModel.setCreatedBy(userDetails.getCreatedBy());
//        userDetailsModel.setCreatedOn(userDetails.getCreatedOn());
        userDetailsModel.setStreetDetail(userDetails.getStreetDetail());
        userDetailsModel.setUpdatedBy(userDetails.getUpdatedBy());
//        userDetailsModel.setUpdatedOn(new Date(userDetails.getUpdatedOn()));
        userDetailsModel.setUserType(userDetails.getUserType());
        userDetailsModel.setCurrentStatusId(userDetails.getCurrentStatusId());
        return userDetailsModel;
    }
    public static byte[] convertMultipartFileToByteArray(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
        return null;
    }
    
    
//    public MultipartFile convertByteArrayToMultipartFile(byte[] bytes, String originalFilename, String contentType) {
//        return new MockMultipartFile(originalFilename, originalFilename, contentType, bytes);
//    }
    
    
//    public MultipartFile convertByteArrayToMultipartFile(byte[] bytes, String originalFilename) {
//        ByteArrayResource resource = new ByteArrayResource(bytes);
//        return new CommonsMultipartFile(resource, originalFilename, MediaType.APPLICATION_OCTET_STREAM_VALUE, bytes.length);
//    }
}

