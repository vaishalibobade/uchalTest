package com.uchal.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.entity.LoginDetails;
import com.uchal.entity.UserDetails;
import com.uchal.mapper.UserDetailsMapper;
import com.uchal.model.ApiException;
import com.uchal.model.SearchUserModel;
import com.uchal.model.SearchUserOutputModel;
import com.uchal.model.SignupModel;
import com.uchal.model.UserDetailsModel;
import com.uchal.model.UserDetailsWithSumModel;
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
	private static final Logger logger = LogManager.getLogger(UserDetailsService.class);

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

	public List<Object[]> getAllEmployeeDetailsWithAssociation(int employeeId) {
		return userDetailsRepository.getEmployeeDetailsByEmployeeId(employeeId);
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

	public String validateSignUpDetails(SignupModel signupModel) {
		String message = null;

		if (loginDetailsService.checkUsernameExist(signupModel.getUsername())) {
			message = "Username is Already Exists";
		}

		if (checkIfMobileExist(signupModel.getMobileNumber())) {
			message = "Mobile Number is Already Exist";
		}
//         System.out.println(message);
		return message;
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

		UserDetails existingUser = checkforNulls(updatedUser);

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

	public UserDetails checkforNulls(UserDetails updatedUser) {

		UserDetails existingUser = userDetailsRepository.getById(updatedUser.getUserId());

		if (updatedUser.getFirstName() != null || !updatedUser.getFirstName().equalsIgnoreCase("null"))
			existingUser.setFirstName(updatedUser.getFirstName());
		if (updatedUser.getAdharImage() != null)
			existingUser.setAdharImage(updatedUser.getAdharImage());
		if (updatedUser.getUserType() == null || !updatedUser.getUserType().equalsIgnoreCase("null")) {
			System.out.println(updatedUser.getUserType());
			existingUser.setUserType(updatedUser.getUserType());
		}
		if (updatedUser.getAdharNumber() != 0)
			existingUser.setAdharNumber(updatedUser.getAdharNumber());
		if (updatedUser.getBloodgroup() != null || !updatedUser.getBloodgroup().equalsIgnoreCase("null"))
			existingUser.setBloodgroup(updatedUser.getBloodgroup());
		if (updatedUser.getCity() != null || !updatedUser.getCity().equalsIgnoreCase("null"))
			existingUser.setCity(updatedUser.getCity());
		if (updatedUser.getCountry() != null || !updatedUser.getCountry().equalsIgnoreCase("null"))
			existingUser.setCountry(updatedUser.getCountry());
		if (updatedUser.getLastName() != null || !updatedUser.getLastName().equalsIgnoreCase("null"))
			existingUser.setLastName(updatedUser.getLastName());
		if (updatedUser.getMiddleName() != null || !updatedUser.getMiddleName().equalsIgnoreCase("null"))
			existingUser.setMiddleName(updatedUser.getMiddleName());
		if (updatedUser.getMobileNumber() != 0)
			existingUser.setMobileNumber(updatedUser.getMobileNumber());
		if (updatedUser.getState() != null || !updatedUser.getState().equalsIgnoreCase("null"))
			existingUser.setState(updatedUser.getState());
		if (updatedUser.getStreetDetail() != null || !updatedUser.getStreetDetail().equalsIgnoreCase("null"))
			existingUser.setStreetDetail(updatedUser.getStreetDetail());
		if (updatedUser.getUpdatedBy() != null)
			existingUser.setUpdatedBy(updatedUser.getUpdatedBy());
		if (updatedUser.getUpdatedOn() != null)
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
		userDetails = checkforNulls(userDetails);

		if (loggeduser.getUserId() != userDetails.getUserId() && masterUserTypeService
				.isAuthorisedCRUD(userDetails.getUserType(), loggeduser.getUserType()) == false) {

			return "you are not Authorised to update details !!!!!!";
		}

		System.out.println(userDetails.getAdharNumber());
		if (userDetails.getAdharNumber() == 0) {
			return "Please Enter Adhar Number";
		}
		if (userDetails.getMobileNumber() == 0) {
			return "Please Enter Mobile Number";
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
	public UserDetails signupUser(SignupModel model) {
		UserDetails userDetails = new UserDetails();
		LoginDetails loginDetails = new LoginDetails();

		UserDetailsMapper usermapper = new UserDetailsMapper();
		try {
			userDetails = usermapper.mapSignupToEntity(model);
			userDetails.setCreatedOn(LocalDateTime.now());
			userDetails.setCurrentStatusId(2);
			userDetails.setRegistrationPaymentAmount(0);
			userDetails.setRegistrationPaymentStatus("Pending");
			userDetails.setRegistrationUnder(0);

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

	@Transactional
	public UserDetails registerUser(UserDetailsModel model) {
		UserDetails userDetails = new UserDetails();
		LoginDetails loginDetails = new LoginDetails();

		UserDetailsMapper usermapper = new UserDetailsMapper();
		try {
			userDetails = usermapper.mapToEntity(model);
			userDetails.setCreatedOn(LocalDateTime.now());
			userDetails.setCurrentStatusId(2);
			userDetails.setRegistrationPaymentAmount(0);
			userDetails.setRegistrationPaymentStatus("Pending");
			userDetails.setRegistrationUnder(userDetails.getCreatedBy());

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
			user.setStatusRemarks(status.getRemarks());
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

	
	
	public Long getRegisteredEmployeeCount()
	{
		return userDetailsRepository.getEmployeeCount();
	}
	
	public Long getRegisteredEmployeeCountunderUser(int id)
	{
		return userDetailsRepository.getEmployeeCountUnderUser(id);
	}
	
	
	
	public List<UserList> getAllUserListbyType(String type) {

		List<Object[]> object = userDetailsRepository.getAllUserListbyType(type);
		List<UserList> list = convertToObjectList(object);

		return list;

	}

//	public List<UserList> getAllUserListbyType(String type) {
//
//		List<Object[]> object = userDetailsRepository.getAllUserListbyType(type);
//		List<UserList> list = convertToObjectList(object);
//
//		return list;
//
//	}

	public List<UserList> getAllVendorSubVendor() {

		List<Object[]> object = userDetailsRepository.getAllVendorSubVendor();
		List<UserList> list = convertToObjectList(object);

		return list;

	}

	public List<SearchUserOutputModel> getsearchSubVendorEmployeeList(SearchUserModel searchUserModel) {

		List<Object[]> object = userDetailsRepository.getDataWithPartialMatchAndMultipleUserType(
				searchUserModel.getAdharNumber(), searchUserModel.getName());
		List<SearchUserOutputModel> list = convertToSearchList(object);

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
		try {
		List<SearchUserOutputModel> userList = new ArrayList<>();

		for (Object[] objArray : originalList) {
			SearchUserOutputModel user = new SearchUserOutputModel();
			String userStatus = (String) objArray[0];
			String firstName = (String) objArray[1];
			String middleName = (String) objArray[2];
			String lastName = (String) objArray[3];
			String userType = (String) objArray[4];
			Long mobileNumber = (Long) objArray[5];
			int userId = (int) objArray[6];
			int registrationUnderId = (int) objArray[7];
			if (registrationUnderId!=0)
			{
			UserDetails registrationUnderObj = userDetailsRepository.getById(registrationUnderId);
			String registrationUnder = registrationUnderObj.getFirstName() + " " + registrationUnderObj.getMiddleName()
					+ " " + registrationUnderObj.getLastName();
			user.setRegistrationUnder(registrationUnder);
			if (registrationUnderObj.getUserType() == null) {
				user.setRegistrationUnderType(masterUserTypeService
						.getMasterUserTypeByAbreviation(registrationUnderObj.getUserType()).getUserType());
			}
			}
			
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
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Issue while fetching data");
			throw new ApiException("Issue while fetching data", 400);
		}

	}

	public List<UserDetailsModel> convertToUserDetails(List<Object[]> objList) {
		List<UserDetailsModel> userList = new ArrayList<>();
		UserDetailsMapper mapper = new UserDetailsMapper();

		for (Object[] objArray : objList) {
			UserDetails userDetails = (UserDetails) objArray[0];
			String currentStatus = (String) objArray[1];
			String userType = (String) objArray[2];
			UserDetailsModel user = mapper.mapToModel(userDetails);
			if (userDetails.getRegistrationUnder() != 0) {
				UserDetails registrationUnderObj = userDetailsRepository.getById(userDetails.getRegistrationUnder());
				String registrationUnder = registrationUnderObj.getFirstName() + " "
						+ registrationUnderObj.getMiddleName() + " " + registrationUnderObj.getLastName();
//		        UserDetailsModel user = new UserDetailsModel();

				user.setRegistrationUnder(registrationUnder);
				user.setRegistrationUnderType(masterUserTypeService
						.getMasterUserTypeByAbreviation(registrationUnderObj.getUserType()).getUserType());
			}
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

		List<Object[]> object = userDetailsRepository.getDataWithPartialMatch(searchUserModel.getAdharNumber(),
				searchUserModel.getName());
		List<SearchUserOutputModel> list = convertToSearchList(object);

		return list;

	}

	public List<SearchUserOutputModel> getSearchUserListwithType(String type, SearchUserModel searchUserModel,int loggedId) {
		System.out.println(searchUserModel.getName());
		System.out.println(searchUserModel.getAdharNumber());
		System.out.println(type);
		List<Object[]> object = userDetailsRepository
//				.getDataWithPartialMatchAndUserType(searchUserModel.getAdharNumber(), searchUserModel.getName(), type,loggedId);
		.getDataWithPartialMatchAndUserType(type,searchUserModel.getAdharNumber(),searchUserModel.getName(),loggedId);

		System.out.println("Object size");
		System.out.println(object.size());
		List<SearchUserOutputModel> list = convertToSearchList(object);
		System.out.println("list length");
		System.out.println(list.size());
		return list;

	}

	// Calculate the sum of amountPaid for each user based on the retrieved list
	public List<UserDetailsWithSumModel> getUserDetailsWithSumAmountPaidByVendor(int vendorId) {
		List<Object[]> userDetailsList = userDetailsRepository.getUserDetailsByVendor(vendorId);

		List<UserDetailsWithSumModel> result = new ArrayList<>();

		for (Object[] object : userDetailsList) {
			UserDetails userDetails = (UserDetails) object[0];
			BigDecimal sumAmountPaid = userDetails.getEmpVendorAssociations().stream()
					.map(EmpVendorAssociation::getAmountPaid).reduce(BigDecimal.ZERO, BigDecimal::add);

			UserDetailsWithSumModel userDetailsWithSum = new UserDetailsWithSumModel();
			userDetailsWithSum.setName(
					userDetails.getFirstName() + " " + userDetails.getMiddleName() + " " + userDetails.getLastName());
			userDetailsWithSum.setTotalAmountPaid(sumAmountPaid);
			userDetailsWithSum.setUserId(userDetails.getUserId());
			userDetailsWithSum.setUserTpe((String) object[1]);

			result.add(userDetailsWithSum);
		}

		return result;
	}
	@Transactional
	public UserDetails updateUserStatus(int userId, int statusId) {
		UserDetails userDetails = userDetailsRepository.getById(userId);
		userDetails.setCurrentStatusId(statusId);
		try {
			userDetails = userDetailsRepository.save(userDetails);
		} catch (Exception e) {
			throw new ApiException("Issue While updating user status", 400);
		}
		return userDetails;

	}

	 public int getCurrentStatusId(int userId)
	 {
		 return userDetailsRepository.getById(userId).getCurrentStatusId();
	 }
	
	
	public boolean checkRegistrationUnder(int userId, int registrationUnderId)
	{
		UserDetails user= userDetailsRepository.getById(userId);
		if( user.getRegistrationUnder()==registrationUnderId)
			return true;
		else 
			return false;
	}
	
}
