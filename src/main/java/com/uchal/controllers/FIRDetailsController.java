package com.uchal.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.FIRDetails;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.FIRDetailsService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.UserDetailsService;

@RestController
//@RequestMapping("/api/firdetails")
public class FIRDetailsController {
	private final FIRDetailsService firDetailsService;
	private final LoginDetailsService loginDetailsService;
	@Autowired

	private static Logger logger = LogManager.getLogger(FIRDetailsController.class);
	private SessionManager sessionManager;

	public FIRDetailsController(UserDetailsService userDetailsService, FIRDetailsService firDetailsService,
			LoginDetailsService loginDetailsService, SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.firDetailsService = firDetailsService;
		this.loginDetailsService = loginDetailsService;

	}

	@PostMapping("/uploadFIR")
	public ResponseEntity<ApiResponse<UserDetailsModel>> createFIRDetails(@RequestBody FIRDetails firDetails,
			@RequestHeader("Authorization") String token) {
		FIRDetails createdFIRDetails = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session != null) {
				UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
				System.out.println(session.getUserId());
				System.out.println(loggedUser.getUserType());
				if (loggedUser.getUserType().equals("E"))

					throw new ApiException("Only Vendor/ Admin can Register !!", 404);
				firDetails.setCreatedBy(loggedUser.getUserId());

			} else {
				throw new ApiException("Session cannot be null !!", 401);

			}
		} else {
			throw new ApiException("Token cannot be null !!", 402);

		}
		firDetails.setCreatedOn(LocalDateTime.now());
		try {
			createdFIRDetails = firDetailsService.createFIRDetails(firDetails);
		} catch (Exception e) {
			throw new ApiException(e.getMessage(), 400);
		}

		return new ResponseEntity(createdFIRDetails, HttpStatus.CREATED);
	}

	@GetMapping("/getAllFIRList")
	public ResponseEntity<List<FIRDetails>> getAllFIRDetails(@RequestHeader("Authorization") String token) {
		List<FIRDetails> allFIRDetails = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session != null) {
				UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
				System.out.println(session.getUserId());
				System.out.println(loggedUser.getUserType());
				if (loggedUser.getUserType().equals("E"))

					throw new ApiException("Only Vendor/ Admin can Register !!", 404);

				allFIRDetails = firDetailsService.getAllFIRDetailsByID(loggedUser.getUserId());

			} else {
				throw new ApiException("Session cannot be null !!", 401);

			}
		} else {
			throw new ApiException("Token cannot be null !!", 402);

		}
		if (allFIRDetails.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(allFIRDetails, HttpStatus.OK);

	}

	// Add other controller methods as needed
}
