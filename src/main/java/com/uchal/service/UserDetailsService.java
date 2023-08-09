package com.uchal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.LoginDetails;
import com.uchal.entity.UserDetails;
import com.uchal.mapper.UserDetailsMapper;
import com.uchal.model.ApiException;
import com.uchal.model.SearchUserModel;
import com.uchal.model.SearchUserOutputModel;
import com.uchal.model.UserDetailsModel;
import com.uchal.model.UserList;
import com.uchal.model.UserStatusModel;
import com.uchal.repository.LoginDetailsRepository;
import com.uchal.repository.UserDetailsRepository;

@Service
public class UserDetailsService {
	private final UserDetailsRepository userDetailsRepository;
	private final LoginDetailsRepository loginDetailsRepository;
	private final LoginDetailsService loginDetailsService;
	private final MasterUserTypeService masterUserTypeService;
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private EntityManager entityManager;

	public UserDetailsService(MasterUserTypeService masterUserTypeService, UserDetailsRepository userDetailsRepository,
			LoginDetailsRepository loginDetailsRepository, LoginDetailsService loginDetailsService) {
		this.userDetailsRepository = userDetailsRepository;
		this.loginDetailsRepository = loginDetailsRepository;
		this.loginDetailsService = loginDetailsService;
		this.masterUserTypeService = masterUserTypeService;

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

	public List<Object[]> getAllEmployeeDetailsWithAssociation(int vendorId) {
		return userDetailsRepository.getEmployeeDetailsByVendorId(vendorId);
	}

	public List<UserDetails> getAllUserDetailsByType(String userType) {
		return userDetailsRepository.findByUserType(userType);
	}

//    public List<UserDetails> getAllLockedUsers() {
//        return userDetailsRepository.findByUserType();
//    }

	public boolean checkIfAdharExist(long adharNumber) {
		if (userDetailsRepository.findAllByAdharNumber(adharNumber).isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean checkIfMobileExist(long mobileNumber) {
		if (userDetailsRepository.findAllByMobileNumber(mobileNumber).isEmpty()) {
			return false;
		}
		return true;
	}

	
	
	public UserDetails getUserDetailsByMobile(long mobileNumber) {
	return userDetailsRepository.findAllByMobileNumber(mobileNumber).get(0);
			
	}

	public String validateUserDetails(UserDetailsModel userDetailsModel) {
		String message = null;

		if (loginDetailsService.checkUsernameExist(userDetailsModel.getUsername())) {
			message = "Username is Already Exists";
		}

		if (checkIfAdharExist(userDetailsModel.getAdharNumber())) {
			message = "Adhar Number is Already Exists";
		}
		if (checkIfMobileExist(userDetailsModel.getMobileNumber())) {
			message = "Mobile Number is Already Exist";
		}
//         System.out.println(message);
		return message;
	}

	public UserDetails updateUser(UserDetails updatedUser) {
//		Optional<UserDetails> user = userDetailsRepository.findById(updatedUser.getUserId());
//		UserDetails existingUser = user.get();
		
		
		UserDetails existingUser =checkforNulls(updatedUser);

		// Update the properties of the existingUser entity with the new values from
		// updatedUser
//		existingUser.setFirstName(updatedUser.getFirstName());
//		existingUser.setAdharImage(updatedUser.getAdharImage());
//		existingUser.setUserType(updatedUser.getUserType());
//		existingUser.setAdharNumber(updatedUser.getAdharNumber());
//		existingUser.setBloodgroup(updatedUser.getBloodgroup());
//		existingUser.setCity(updatedUser.getCity());
//		existingUser.setCountry(updatedUser.getCountry());
//		existingUser.setLastName(updatedUser.getLastName());
//		existingUser.setMiddleName(updatedUser.getMiddleName());
//		existingUser.setMobileNumber(updatedUser.getMobileNumber());
//		existingUser.setState(updatedUser.getState());
//		existingUser.setStreetDetail(updatedUser.getStreetDetail());
//		existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
//		existingUser.setUpdatedOn(updatedUser.getUpdatedOn());

		// Save the updated entity
		return userDetailsRepository.save(existingUser);
	}

	
	
	public UserDetails checkforNulls(UserDetails updatedUser)
	{
		
		UserDetails existingUser=userDetailsRepository.getById(updatedUser.getUserId());
		
		if(updatedUser.getFirstName()!=null || updatedUser.getFirstName().equals("null"))
			existingUser.setFirstName(updatedUser.getFirstName());
			if(updatedUser.getAdharImage()!=null)
			existingUser.setAdharImage(updatedUser.getAdharImage());
			if(updatedUser.getUserType()!=null|| updatedUser.getUserType().equals("null"))
			{
				System.out.println(updatedUser.getUserType());
			existingUser.setUserType(updatedUser.getUserType());
			}
			if(updatedUser.getAdharNumber()!=0)
			existingUser.setAdharNumber(updatedUser.getAdharNumber());
			if(updatedUser.getBloodgroup()!=null|| updatedUser.getBloodgroup().equals("null"))
			existingUser.setBloodgroup(updatedUser.getBloodgroup());
			if(updatedUser.getCity()!=null|| updatedUser.getCity().equals("null"))
			existingUser.setCity(updatedUser.getCity());
			if(updatedUser.getCountry()!=null|| updatedUser.getCountry().equals("null"))
			existingUser.setCountry(updatedUser.getCountry());
			if(updatedUser.getLastName()!=null|| updatedUser.getLastName().equals("null"))
			existingUser.setLastName(updatedUser.getLastName());
			if(updatedUser.getMiddleName()!=null|| updatedUser.getMiddleName().equals("null"))
			existingUser.setMiddleName(updatedUser.getMiddleName());
			if(updatedUser.getMobileNumber()!=0)
			existingUser.setMobileNumber(updatedUser.getMobileNumber());
			if(updatedUser.getState()!=null|| updatedUser.getState().equals("null"))
			existingUser.setState(updatedUser.getState());
			if(updatedUser.getStreetDetail()!=null|| updatedUser.getStreetDetail().equals("null"))
			existingUser.setStreetDetail(updatedUser.getStreetDetail());
			if(updatedUser.getUpdatedBy()!=null|| updatedUser.getUpdatedBy().equals("null"))
			existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
			if(updatedUser.getUpdatedOn()!=null|| updatedUser.getUpdatedOn().equals("null"))
			existingUser.setUpdatedOn(updatedUser.getUpdatedOn());
			return existingUser;

	}
	public String validateUpdateUserDetails(UserDetails userDetails, UserDetails loggeduser) {
		String message = null;
		if (userDetailsRepository.findById(userDetails.getUserId()).isEmpty()) {

			System.out.println("In validate fun start 1");
			message = "User not found with ID: " + userDetails.getUserId();
			return message;
		}
//		System.out.println(loggeduser.getUserId());
//		System.out.println(userDetails.getUserId());
//		System.out.println(masterUserTypeService
//				.isAuthorisedCRUD(userDetails.getUserType(), loggeduser.getUserType()));
		userDetails=checkforNulls(userDetails);
				
		if (loggeduser.getUserId() != userDetails.getUserId() && masterUserTypeService
				.isAuthorisedCRUD(userDetails.getUserType(), loggeduser.getUserType()) == false) {

			return "you are not Authorised to update details !!!!!!";
		}
		
		if (!userDetailsRepository.findByAdharIdNotEqual(userDetails.getUserId(), userDetails.getAdharNumber())
				.isEmpty() && message == null) {
			message = "Adhar Number is Already Exists";
		}
		if (!userDetailsRepository.findByMobileIdNotEqual(userDetails.getUserId(), userDetails.getMobileNumber())
				.isEmpty() && message == null) {
			message = "Mobile Number is Already Exist";
		}
		System.out.println("In validate fun");
		return message;

	}

	@Transactional
	public UserDetails registerUser(UserDetailsModel model) {
		UserDetails userDetails = new UserDetails();
		LoginDetails loginDetails = new LoginDetails();

		UserDetailsMapper usermapper = new UserDetailsMapper();
		try {
			userDetails = usermapper.mapToEntity(model);
			userDetails.setCreatedOn(LocalDateTime.now());
			userDetails.setCurrentStatusId(2);

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

		} catch (Exception e) {
			System.out.println(e);
		}

//String disableIdentityInsertSql = "SET IDENTITY_INSERT user_details OFF";
//entityManager.createNativeQuery(disableIdentityInsertSql).executeUpdate();
		return userDetails;
	}

	public UserDetails updateCurrentStatus(UserStatusModel status) {

		UserDetails user = getUserDetailsById(status.getUserId());
		if (user != null) {
			user.setCurrentStatusId(status.getCurrentStatusId());
			user.setUpdatedBy(status.getUpdatedBy());
			user.setUpdatedOn(LocalDateTime.now());
			logger.info(user);
			logger.error("Saving user details...........");
			userDetailsRepository.save(user);
		} else {
			logger.error("User is not found");
			logger.error("Throwing Runtime Exception ...........!!!!!!!!!");
			throw new ApiException("User is not found", 404);

		}

		return user;

	}

	public UserDetailsModel unlockUser(int loggedUID, int unlockId) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		LoginDetails details = null;
		UserDetailsModel userModel = null;
		UserDetails user = getUserDetailsById(unlockId);
		if (loginDetailsService.getUserStatus(unlockId).equals("L")) {
			details = loginDetailsService.setUserStatus(loggedUID, unlockId, "A");
			userModel = mapper.mapToModel(user);
		}

		return userModel;

	}

	public List<UserList> getAllUserList() {

		List<Object[]> object = userDetailsRepository.getAllUserList();
		List<UserList> list = convertToObjectList(object);

		return list;

	}
	public List<UserList> getAllUserListbyType(String type) {

		List<Object[]> object = userDetailsRepository.getAllUserListbyType(type);
		List<UserList> list = convertToObjectList(object);

		return list;

	}
	public List<UserList> getAllVendorSubVendor() {

		List<Object[]> object = userDetailsRepository.getAllVendorSubVendor();
		List<UserList> list = convertToObjectList(object);

		return list;

	}
	
	
	
	

	public List<UserList> convertToObjectList(List<Object[]> originalList) {
		List<UserList> userList = new ArrayList<>();

		for (Object[] objArray : originalList) {
			int userId = (int) objArray[0];
			String firstName = (String) objArray[1];
			String middleName = (String) objArray[2];
			String lastName = (String) objArray[3];
			String userType = (String) objArray[4];

			UserList user = new UserList();
			user.setUserId(userId);
			String name = firstName + " " + middleName + " " + lastName;
			user.setFirstName(name);
//	        user.setMiddleName(middleName);
//	        user.setLastName(lastName);
			user.setUserType(userType);

			userList.add(user);
		}

		return userList;
	}

	public List<SearchUserOutputModel> convertToSearchList(List<Object[]> originalList) {
		List<SearchUserOutputModel> userList = new ArrayList<>();

		for (Object[] objArray : originalList) {
			String userStatus = (String) objArray[0];
			String firstName = (String) objArray[1];
			String middleName = (String) objArray[2];
			String lastName = (String) objArray[3];
			String userType = (String) objArray[4];
			Long mobileNumber = (Long) objArray[5];
			int userId = (int) objArray[6];

			SearchUserOutputModel user = new SearchUserOutputModel();
			String name = firstName + " " + middleName + " " + lastName;

			user.setFirstName(name);
//			user.setMiddleName(middleName);
//			user.setLastName(lastName);
			user.setUserType(userType);
			user.setMobileNumber(mobileNumber);
			user.setCurrentStatus(userStatus);
			user.setUserId(userId);

			userList.add(user);
		}

		return userList;
	}

	public List<UserDetailsModel> convertToUserDetails(List<Object[]> objList) {
		List<UserDetailsModel> userList = new ArrayList<>();
		UserDetailsMapper mapper = new UserDetailsMapper();

		for (Object[] objArray : objList) {
			UserDetails userDetails = (UserDetails) objArray[0];
			String currentStatus = (String) objArray[1];
			String userType = (String) objArray[2];

//		        UserDetailsModel user = new UserDetailsModel();
			UserDetailsModel user = mapper.mapToModel(userDetails);
			user.setCurrentStatus(currentStatus);
			user.setUserType(userType);
			userList.add(user);
		}
		return userList;
	}

	public UserDetailsModel getProfileDetails(int userId) {
		List<Object[]> objList = userDetailsRepository.getUserDetailsById(userId);
		UserDetailsModel userDetails = convertToUserDetails(objList).get(0);

		return userDetails;
	}

	public List<SearchUserOutputModel> getSearchUserList(SearchUserModel searchUserModel) {

		List<Object[]> object = userDetailsRepository.getDataWithPartialMatch(searchUserModel.getMobileNumber(),
				searchUserModel.getName());
		List<SearchUserOutputModel> list = convertToSearchList(object);

		return list;

	}

	public List<SearchUserOutputModel> getSearchUserListwithType(String type, SearchUserModel searchUserModel) {

		List<Object[]> object = userDetailsRepository
				.getDataWithPartialMatchAndUserType(searchUserModel.getMobileNumber(), searchUserModel.getName(), type);
		List<SearchUserOutputModel> list = convertToSearchList(object);

		return list;

	}
}
