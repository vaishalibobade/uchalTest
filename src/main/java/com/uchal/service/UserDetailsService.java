package com.uchal.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.LoginDetails;
import com.uchal.entity.UserDetails;
import com.uchal.mapper.UserDetailsMapper;
import com.uchal.model.ApiException;
import com.uchal.model.UserDetailsModel;
import com.uchal.model.UserStatusModel;
import com.uchal.repository.LoginDetailsRepository;
import com.uchal.repository.UserDetailsRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
	private final LoginDetailsRepository loginDetailsRepository;
	private final LoginDetailsService loginDetailsService;
	private static final Logger logger = LogManager.getLogger(LoginController.class);



    @Autowired
    private EntityManager entityManager;

    public UserDetailsService(UserDetailsRepository userDetailsRepository,LoginDetailsRepository loginDetailsRepository,LoginDetailsService loginDetailsService) {
        this.userDetailsRepository = userDetailsRepository;
		this.loginDetailsRepository = loginDetailsRepository ;
		this.loginDetailsService = loginDetailsService;
        
    }

    public UserDetails saveUserDetails(UserDetails userDetails) {    	
        return userDetailsRepository.save(userDetails);
    }

    public UserDetails getUserDetailsById(int userId) {
        return userDetailsRepository.findById(userId).orElse(null);
    }

    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    public void deleteUserDetails(int userId) {
        userDetailsRepository.deleteById(userId);
    }

    // Other methods as needed
    
    public UserDetails getUserDetailsByUsername(String username) {
    	return userDetailsRepository.findByFirstName(username);
    	
    }
    
    public boolean checkIfAdharExist(long adharNumber)
    {
    	if(userDetailsRepository.findAllByAdharNumber(adharNumber).isEmpty())
    	{
    		return false;
    	}
    	return true;
    }
    
    
    public boolean checkIfMobileExist(long mobileNumber)
    {
    	if(userDetailsRepository.findAllByMobileNumber(mobileNumber).isEmpty())
    	{
    		return false;
    	}
    	return true;
    }
    
    
    
    
    
    public String validateUserDetails(UserDetailsModel userDetailsModel)
    {
    	String message=null;
    	
    	if (loginDetailsService.checkUsernameExist(userDetailsModel.getUsername()))
		{
     		message = "Username is Already Exists";
		}
    	
     	if (checkIfAdharExist(userDetailsModel.getAdharNumber()))
		{
     		message = "Adhar Number is Already Exists";
		}
         if (checkIfMobileExist(userDetailsModel.getMobileNumber()))
         {
        	 message="Mobile Number is Already Exist";
         }
//         System.out.println(message);
         return message;
    }
    
    
    public UserDetails updateUser( UserDetails updatedUser)
    {
    	Optional<UserDetails> user = userDetailsRepository.findById(updatedUser.getUserId());
        UserDetails existingUser=user.get();
        // Update the properties of the existingUser entity with the new values from updatedUser
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setAdharImage(updatedUser.getAdharImage());
        existingUser.setUserType(updatedUser.getUserType());
        existingUser.setAdharNumber(updatedUser.getAdharNumber());   
        existingUser.setBloodgroup(updatedUser.getBloodgroup());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setCountry(updatedUser.getCountry());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setMiddleName(updatedUser.getMiddleName());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());
        existingUser.setState(updatedUser.getState());
        existingUser.setStreetDetail(updatedUser.getStreetDetail());
        existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
        existingUser.setUpdatedOn(updatedUser.getUpdatedOn());
        
        // Save the updated entity
        return userDetailsRepository.save(existingUser);
    }
    
    
    public String validateUpdateUserDetails(UserDetails userDetails)
    {
    	System.out.println("In validate fun start");
        	String message=null;
        	if(userDetailsRepository.findById(userDetails.getUserId()).isEmpty())
        	{

            	System.out.println("In validate fun start 1");
        		message="User not found with ID: "+userDetails.getUserId();
        	}
         	if (!userDetailsRepository.findByAdharIdNotEqual(userDetails.getUserId(),userDetails.getAdharNumber()).isEmpty() && message==null)
    		{
         		message = "Adhar Number is Already Exists";
    		}
         	if (!userDetailsRepository.findByMobileIdNotEqual(userDetails.getUserId(),userDetails.getMobileNumber()).isEmpty() && message==null)
             {
            	 message="Mobile Number is Already Exist";
             }
    	System.out.println("In validate fun");
    	return message;
    	
    	
    	
    }
    
    @Transactional
	public UserDetails registerUser(UserDetailsModel model)
    {   	UserDetails userDetails= new UserDetails();
	 		LoginDetails loginDetails= new LoginDetails();

		UserDetailsMapper usermapper = new UserDetailsMapper();
try {
	userDetails=usermapper.mapToEntity(model);
	userDetails.setCreatedOn(LocalDateTime.now());

	
	
	
	
    	loginDetails.setUsername(model.getUsername());
    	loginDetails.setPassword(model.getPassword());
    	loginDetails.setUserStatus("A");
    	loginDetails.setLoginAttempts(0);
//    	user.setLoginDetails(loginDetails);
//    	UserDetails mergedUserDetails = entityManager.merge(user);
    	loginDetails.setUserDetails(userDetails);

//    	user.setLoginDetails(loginDetails);

    	
    	
   

    	// Establish the one-to-one relationship
    	userDetails.setLoginDetails(loginDetails);
    	loginDetails.setUserDetails(userDetails);

    	// Save the records
    	logger.error("tring to save UserDetails");
//    	String enableIdentityInsertSql = "SET IDENTITY_INSERT user_details ON";
//    	entityManager.createNativeQuery(enableIdentityInsertSql).executeUpdate();


    	userDetailsRepository.save(userDetails);
    	
    	
    


//    	user=userDetailsRepository.save(user);
//    	loginDetailsRepository.save(loginDetails);
    	logger.error("saved UserDetails");


}
catch (Exception e) {
System.out.println(e);
}
    	
//String disableIdentityInsertSql = "SET IDENTITY_INSERT user_details OFF";
//entityManager.createNativeQuery(disableIdentityInsertSql).executeUpdate();
    	return userDetails;
    }
    
    
    public UserDetails updateCurrentStatus (UserStatusModel status)
    {
    	
    	UserDetails user = getUserDetailsById (status.getUserId());
    	 if (user != null)
    	 {
    		 user.setCurrentStatusId(status.getCurrentStatusId());
    		 user.setUpdatedBy(status.getUpdatedBy());
    		 user.setUpdatedOn(LocalDateTime.now());
    		 logger.info(user);
    		 logger.error("Saving user details...........");
    		userDetailsRepository.save(user);
    	 }
    	 else
    	 {
    		 logger.error("User is not found");
    		 logger.error("Throwing Runtime Exception ...........!!!!!!!!!");
    		 throw new ApiException("User is not found",404);

    	 }
    	
    	
    	
    	
    	return user;
    	
    }
    
    
    public UserDetailsModel unlockUser (int loggedUID, int unlockId)
    {
		UserDetailsMapper mapper = new UserDetailsMapper();
		LoginDetails details=null;
		UserDetailsModel userModel=null;
    	UserDetails user=getUserDetailsById(unlockId);  
    	if (loginDetailsService.getUserStatus(unlockId).equals("L"))
    	{
    	details =  loginDetailsService.setUserStatus(loggedUID,unlockId,"A");
    	 userModel=mapper.mapToModel(user);
    	}
    	
    	
		return userModel;
    	
    }
    
    
    
}

