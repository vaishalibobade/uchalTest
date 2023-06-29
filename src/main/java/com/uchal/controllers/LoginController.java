package com.uchal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.LoginDetails;
import com.uchal.model.ApiResponse;
import com.uchal.model.LoginRequest;
import com.uchal.model.LoginResponse;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.LoginDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@Component
public class LoginController {
	private SessionManager sessionManager;
	private static final Logger logger = LogManager.getLogger(LoginController.class);

//    private final UserRepository userRepository;
//    @Autowired
//    public LoginController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

	private final LoginDetailsService loginDetailsService;

	@Autowired
	public LoginController(LoginDetailsService loginDetailsRepository, SessionManager sessionManager) {
		this.loginDetailsService = loginDetailsRepository;
		this.sessionManager = sessionManager;
	}

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//        User user = userRepository.findByUsername(request.getUsername());
//
//        if (user != null && user.getPassword().equals(request.getPassword())) {
//            // Authentication successful, generate token
//            String token = generateToken();
//            LoginResponse response = new LoginResponse();
//            response.setAuthenticated(true);
//            response.setToken(token);
//            return ResponseEntity.ok(response);
//        } else {
//            // Authentication failed
//            LoginResponse response = new LoginResponse();
//            response.setAuthenticated(false);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginDetails>> login(@RequestBody LoginRequest request) {
		ResponseEntity<ApiResponse<LoginDetails>> response;
		String token = null;
		LoginDetails user = loginDetailsService.getByUsername(request.getUsername());
		LoginResponse loginResponse = new LoginResponse();
		HttpStatus httpStatus = null;
		String message = null;
		LoginDetails loginDetailsresponse = null;
		try {

			logger.info("Into login controller with logger");
			if (loginDetailsService.validateUserToLogin(user)) // If user status is Active
			{
//        	System.out.println(loginDetailsService.validateUserToLogin(user));
				if (user != null && user.getPassword().equals(request.getPassword())) {
					loginDetailsService.setLoggedInUser(user);
					// Authentication successful, generate token
					SessionToken sessionToken = sessionManager.createSession(user.getUsername());
					token = sessionToken.getToken();
					loginResponse.setAuthenticated(true);
//            loginResponse.setToken(token);
					httpStatus = HttpStatus.OK;
					message = "Success";
					loginDetailsresponse = user;
				} else {
					LoginDetails loginDetails = loginDetailsService.updateLoginAttempts(request.getUsername());
					// Authentication failed
					loginResponse.setAuthenticated(false);
					httpStatus = HttpStatus.UNAUTHORIZED;
					message = "Invalid Username/Password !!";
					loginDetailsresponse = loginDetails;
				}
			}
			if (!loginDetailsService.validateUserToLogin(user)) {
				loginResponse.setAuthenticated(false);
				httpStatus = HttpStatus.UNAUTHORIZED;
				message = "Account is Locked !!";
				loginDetailsresponse = user;

			}
		} catch (Exception e) {
			loginResponse.setAuthenticated(false);
			httpStatus = HttpStatus.UNAUTHORIZED;
			message = "Invalid Username/Password !!";
			loginDetailsresponse = user;

		}
		loginDetailsresponse.setPassword(null);
		return ResponseEntity.status(httpStatus)
				.body(new ApiResponse<>(httpStatus, message, loginDetailsresponse, token));

	}

//	@PutMapping("/changePassword/{username}")
//	public ResponseEntity<ApiResponse<UserDetailsModel>> forgotPassword(@PathVariable("username") String username)
//	{
//		
//		
//	}
	
	
	
	
	
	private String generateToken() {
		// Implement your token generation logic here
		return "sample-token";
	}
}