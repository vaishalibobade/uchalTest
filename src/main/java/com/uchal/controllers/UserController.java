package com.uchal.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.LoginDetails;
import com.uchal.entity.UserDetails;
import com.uchal.mapper.UserDetailsMapper;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.ChangePassword;
import com.uchal.model.SearchUserModel;
import com.uchal.model.SearchUserOutputModel;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.model.UserList;
import com.uchal.model.UserStatusModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.UserDetailsService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })

@RequestMapping("/user")
public class UserController {
	private final UserDetailsService userDetailsService;
	private final LoginDetailsService loginDetailsService;
	private SessionManager sessionManager;

	@Autowired
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	public UserController(UserDetailsService userDetailsService, LoginDetailsService loginDetailsService,
			SessionManager sessionManager) {
		this.userDetailsService = userDetailsService;
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;

	}

	@PostMapping("/signUp")
	public ResponseEntity<ApiResponse<UserDetailsModel>> signUpUser(@RequestBody UserDetailsModel userDetailsModel) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		String message = null;
		
//			String sessionToken = token.substring(7); // Remove "Bearer " prefix
//			SessionToken session = sessionManager.getSessionToken(sessionToken);
//			if (session != null) {
//				UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
//				System.out.println(session.getUserId());
//				System.out.println(loggedUser.getUserType());
//				if (loggedUser.getUserType().equals("E"))
//
//					throw new ApiException("Only Vendor/ Admin can Register !!", 404);
//				userDetailsModel.setCreatedBy(loggedUser.getUserId());
//
//			}
		

		message = userDetailsService.validateUserDetails(userDetailsModel);
		if (message == null) {
			UserDetails savedUser = userDetailsService.registerUser(userDetailsModel);

			if (savedUser != null) {
				userDetailsModel = mapper.mapToModel(savedUser);
				message = "User registered successfully";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				message = "Issue while registering User";
			}
		} else {
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userDetailsModel, null));

	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDetailsModel>> registerUser(@RequestBody UserDetailsModel userDetailsModel,
			@RequestHeader("Authorization") String token) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		String message = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session != null) {
				UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
				System.out.println(session.getUserId());
				System.out.println(loggedUser.getUserType());
				if (loggedUser.getUserType().equals("E"))

					throw new ApiException("Only Vendor/ Admin can Register !!", 404);
				userDetailsModel.setCreatedBy(loggedUser.getUserId());

			}
		}

		message = userDetailsService.validateUserDetails(userDetailsModel);
		if (message == null) {
			UserDetails savedUser = userDetailsService.registerUser(userDetailsModel);

			if (savedUser != null) {
				userDetailsModel = mapper.mapToModel(savedUser);
				message = "User registered successfully";
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				message = "Issue while registering User";
			}
		} else {
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userDetailsModel, token));

	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<UserDetailsModel>> updateUser(@RequestBody UserDetailsModel userDetailsModel,
			@RequestHeader("Authorization") String token) {

		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//		System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E") && loggedUser.getUserId() != userDetailsModel.getUserId())

				throw new ApiException("Only Vendor/ Admin can update !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		message = userDetailsService.validateUpdateUserDetails(mapper.mapToEntity(userDetailsModel), loggedUser);
//		System.out.println("in update111");
		if (message == null) {
			UserDetails updatedUser = userDetailsService.updateUser(mapper.mapToEntity(userDetailsModel));
			if (updatedUser != null) {
				userDetailsModel = mapper.mapToModel(updatedUser);
				message = "User Updated successfully";
				httpStatus = HttpStatus.OK;
			} else {
				throw new ApiException("Issue while updating User", 402);
			}
		} else {
			throw new ApiException(message, 404);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userDetailsModel, token));

	}

	@GetMapping("/veiwProfile")
	public ResponseEntity<ApiResponse<UserDetailsModel>> veiwUserProfile(@RequestHeader("Authorization") String token) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		String message = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
//			System.out.println(session.getUserId());
//			System.out.println(loggedUser.getUserType());
			UserDetailsModel userDetails = userDetailsService.getProfileDetails(loggedUser.getUserId());
			if (userDetails == null) {
				throw new ApiException("User not found", 400);
			} else {
				user = userDetails;
				httpStatus = HttpStatus.OK;
				message = "User found";
			}
		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 401);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, user, token));

	}

	@PutMapping("/updateProfile")
	public ResponseEntity<ApiResponse<UserDetailsModel>> updateUserProfile(
			@RequestBody UserDetailsModel userDetailsModel, @RequestHeader("Authorization") String token) {

		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//		System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session == null)
			throw new ApiException("Session cannot be null !!!!!!", 400);
		loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
		System.out.println(session.getUserId());
		System.out.println(loggedUser.getUserType());
		userDetailsModel.setUserId(loggedUser.getUserId());
		if (loggedUser.getUserType().equals("E") && loggedUser.getUserId() != userDetailsModel.getUserId())
			throw new ApiException("Only Vendor/ Admin can update !!", 401);
		message = userDetailsService.validateUpdateUserDetails(mapper.mapToEntity(userDetailsModel), loggedUser);
//		System.out.println("in update111");
		if (message == null) {
			UserDetails updatedUser = userDetailsService.updateUser(mapper.mapToEntity(userDetailsModel));
			if (updatedUser != null) {
				userDetailsModel = mapper.mapToModel(updatedUser);
				message = "User Updated successfully";
				httpStatus = HttpStatus.OK;
			} else {
				throw new ApiException("Issue while updating User", 402);
			}
		} else {
			throw new ApiException(message, 403);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userDetailsModel, token));

	}

	@GetMapping("/veiw/{id}")
	public ResponseEntity<ApiResponse<UserDetailsModel>> veiwUser(@PathVariable("id") int id,
			@RequestHeader("Authorization") String token) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		String message = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			UserDetailsModel userDetails = userDetailsService.getProfileDetails(id);
			if (userDetails == null) {
				throw new ApiException("User not found", 401);
			} else {
				user = userDetails;
				httpStatus = HttpStatus.OK;
				message = "User found";
			}
		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, user, token));

	}

	@GetMapping("/veiwAll")
	public ResponseEntity<ApiResponse<List<UserDetails>>> veiwAllUser(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<UserDetails> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			userList = userDetailsService.getAllUserDetails();
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}
	
	
	//Search Employee for Update status,...............Admin User.

	@GetMapping("/searchUser")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> searchUser(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		SearchUserModel searchUserModel = new SearchUserModel();
		searchUserModel.setMobileNumber(mobileNumber);
		searchUserModel.setName(name);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			userList = userDetailsService.getSearchUserList(searchUserModel);
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	@GetMapping("/veiwAllList")
	public ResponseEntity<ApiResponse<List<UserList>>> veiwAllUserList(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<UserList> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			userList = userDetailsService.getAllUserList();
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}
	
	
	@GetMapping("/veiwUserforPaymentList")
	public ResponseEntity<ApiResponse<List<UserList>>> veiwUserforPaymentList(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<UserList> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);
			
			
			
		if (loggedUser.getUserType().equals("A"))
			userList = userDetailsService.getAllUserListbyType("V");
		if (loggedUser.getUserType().equals("V"))
			userList = userDetailsService.getAllVendorSubVendor();
		if (loggedUser.getUserType().equals("S"))
			userList = userDetailsService.getAllUserListbyType("E");



			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	///Search User for Upload FIR Fuctionality.
	
	@GetMapping("/veiwUserforFIRList")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> searchUserFIRupload(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber,@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		SearchUserModel searchUserModel=new SearchUserModel();
		searchUserModel.setName(name);
		searchUserModel.setMobileNumber(mobileNumber);
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);
			
			
			
		if (loggedUser.getUserType().equals("A"))
			userList = userDetailsService.getSearchUserList(searchUserModel);
		if (loggedUser.getUserType().equals("V"))
			userList = userDetailsService.getsearchSubVendorEmployeeList(searchUserModel);
		if (loggedUser.getUserType().equals("S"))
			userList = userDetailsService.getSearchUserListwithType("E",searchUserModel);



			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	
	@PutMapping("/updateStatus")
	public ResponseEntity<ApiResponse<UserDetailsModel>> updateStatus(@RequestBody UserStatusModel userStatusModel,
			@RequestHeader("Authorization") String token) {
		UserDetailsMapper mapper = new UserDetailsMapper();
		HttpStatus httpStatus;
		String message = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (!loggedUser.getUserType().equals("A")) {
				logger.error("Logged In User is not Admin !!!!!!!!!!!!!!!!!! Throw Exception");
				throw new ApiException("Only Admin can change status .....!!", 400);
			}
//			UserDetails userDetails = userDetailsService.getUserDetailsById(userStatusModel.getUserId());
			if (userDetailsService.getUserDetailsById(userStatusModel.getUserId()) == null) {
				throw new ApiException("User not found", 401);
			} else {
				userStatusModel.setUpdatedBy(loggedUser.getUserId());
				UserDetails userDetails = userDetailsService.updateCurrentStatus(userStatusModel);
				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
			}
		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, user, token));
	}

	@PutMapping("/unlockUser/{id}")
	public ResponseEntity<ApiResponse<UserDetailsModel>> unlockUser(@PathVariable("id") int id,
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);

		if (session != null) {
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E")) {
				logger.error("Logged In User is not Admin or Vendor !!!!!!!!!!!!!!!!!! Throw Exception");
				throw new ApiException("Only Admin/Vendor can unlock user .....!!", 400);
			}

			user = userDetailsService.unlockUser(loggedUser.getUserId(), id);
			if (user != null) {
				httpStatus = HttpStatus.OK;
				message = "User Unlocked Successfully !!";
			} else {
				throw new ApiException("User is not locked  !!", 401);
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, user, token));
	}

	@GetMapping("/veiwAllEmployee")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> veiwAllEmployee(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		SearchUserModel searchUserModel = new SearchUserModel();
		searchUserModel.setMobileNumber(mobileNumber);
		searchUserModel.setName(name);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			userList = userDetailsService.getSearchUserListwithType("E", searchUserModel);
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 404);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 401);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	@GetMapping("/veiwAllSubVendor")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> veiwAllSubvendor(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		SearchUserModel searchUserModel = new SearchUserModel();
		searchUserModel.setMobileNumber(mobileNumber);
		searchUserModel.setName(name);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);

			userList = userDetailsService.getSearchUserListwithType("S", searchUserModel);
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	@GetMapping("/veiwAllVendor")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> veiwAllVendor(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		SearchUserModel searchUserModel = new SearchUserModel();
		searchUserModel.setMobileNumber(mobileNumber);
		searchUserModel.setName(name);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E") || loggedUser.getUserType().equals("S"))

				throw new ApiException(" Only Admin/Vendor can veiw !!", 400);
			System.out.println("above fun call");
			userList = userDetailsService.getSearchUserListwithType("V", searchUserModel);
			System.out.println("Below fun call");
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	@GetMapping("/veiwAllAdmin")
	public ResponseEntity<ApiResponse<List<SearchUserOutputModel>>> veiwAllAdmin(@RequestParam("name") String name,
			@RequestParam("mobileNumber") long mobileNumber, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		List<SearchUserOutputModel> userList = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		SearchUserModel searchUserModel = new SearchUserModel();
		searchUserModel.setMobileNumber(mobileNumber);
		searchUserModel.setName(name);
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			logger.info(loggedUser.getUserId());
			logger.info(loggedUser.getUserType());
			if (!loggedUser.getUserType().equals("A"))

				throw new ApiException(" Only Admin  can veiw !!", 400);
			System.out.println("above fun call");
			userList = userDetailsService.getSearchUserListwithType("A", searchUserModel);
			System.out.println("Below fun call");
			if (userList.isEmpty()) {
				throw new ApiException("No record found", 401);
			} else {
//				user = mapper.mapToModel(userDetails);
				httpStatus = HttpStatus.OK;
				message = "User found";
				System.out.println(userList.size());
			}

		} else {
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 402);
		}

		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));

	}

	@PutMapping("/changePassword")
	public ResponseEntity<ApiResponse<LoginDetails>> forgotPassword(@RequestBody ChangePassword changePasswordModel) {
		String message = null;
//		HttpStatus httpStatus = null;

		LoginDetails loginDetails = null;

		UserDetails user = userDetailsService
				.getUserDetailsByMobile(Long.parseLong(changePasswordModel.getMobileNumber()));
		if (user == null)
			throw new ApiException("Invalid Mobile number !!", 400);

		else {
			changePasswordModel.setUserName(user.getLoginDetails().getUsername());

			loginDetails = loginDetailsService.updatePassword(changePasswordModel);
		}
		if (user != null)
			message = "Password updated Successfully !!!";
		else
			throw new ApiException("Issue while changing password ......", 401);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, null, null));

	}

//	@GetMapping("/veiwAllLocked")
//	public ResponseEntity<ApiResponse<List<UserDetails>>> veiwAllLockedUser(@RequestHeader("Authorization") String token) {
//		HttpStatus httpStatus = HttpStatus.OK;
//		String message = null;
//		List<UserDetails> userList = null;
//		String sessionToken = token.substring(7); // Remove "Bearer " prefix
//		SessionToken session = sessionManager.getSessionToken(sessionToken);
//
//		if (session != null) {
//			// User is authenticated, process the protected resource request
////        @SuppressWarnings("removal")
////        return "Protected Resource for User: " + userId;
//			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
//			logger.info(loggedUser.getUserId());
//			logger.info(loggedUser.getUserType());
//			if (loggedUser.getUserType().equals("E"))
//
//				throw new ApiException(" Only Admin/Vendor can veiw !!", 404);
//
//			userList = userDetailsService.getAllUserDetailsByType("E");
//			if (userList.isEmpty()) {
//				throw new ApiException("No record found", 404);
//			} else {
////				user = mapper.mapToModel(userDetails);
//				httpStatus = HttpStatus.OK;
//				message = "User found";
//				System.out.println(userList.size());
//			}
//
//		} else {
//			// Invalid session token or unauthorized access
//			throw new ApiException("Invalid session token or unauthorized access", 404);
//		}
//
//		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userList, token));
//
//	}
//	
//	

}
