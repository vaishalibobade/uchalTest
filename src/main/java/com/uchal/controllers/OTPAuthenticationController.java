package com.uchal.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.OtpService;
import com.uchal.service.UserDetailsService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class OTPAuthenticationController {

	private SessionManager sessionManager;
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	private final UserDetailsService userDetailsService;
	private final OtpService otpService;

	@Autowired
	public OTPAuthenticationController(UserDetailsService userDetailsService, SessionManager sessionManager,
			OtpService otpService) {
		this.userDetailsService = userDetailsService;
		this.sessionManager = sessionManager;
		this.otpService = otpService;
	}

	@PostMapping("/generateOtp")
	public ResponseEntity<ApiResponse<String>> generateOtp(@RequestParam("phoneNumber") String phoneNumber) {
		
			if (userDetailsService.checkIfMobileExist(Long.parseLong(phoneNumber))) {
				otpService.generateOtp(phoneNumber);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse<>(HttpStatus.OK, "OTP generated and sent successfully", null, null));

//			return ResponseEntity.ok("OTP generated and sent successfully");
			} else {
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mobile number is not registered Mobile number");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
						HttpStatus.INTERNAL_SERVER_ERROR, "Mobile number is not registered Mobile number", phoneNumber, null));

			}
		
		
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<ApiResponse<String>>  verifyOtp(@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("otp") String otp) {
		boolean isOtpValid = otpService.verifyOtp(phoneNumber, otp);
		if (isOtpValid) {
//			return ResponseEntity.ok("OTP verification successful");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(HttpStatus.OK, "OTP verification successful", null, null));

		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse<>(HttpStatus.UNAUTHORIZED, "Invalid OTP", phoneNumber, null));

		}
	}

}
