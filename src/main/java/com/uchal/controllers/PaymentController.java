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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.MasterPaymentStatus;
import com.uchal.entity.MasterUserStatus;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.EmpVendorAssociationModel;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.EmpVendorAssociationService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.MasterPaymentStatusService;

@RestController
@Component 
@CrossOrigin(origins = "*")

public class PaymentController {
	private final LoginDetailsService loginDetailsService;
	private final EmpVendorAssociationService empVendorAssociationService;
	private SessionManager sessionManager;
	private final MasterPaymentStatusService masterPaymentStatusService;
	@Autowired
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	public PaymentController(SessionManager sessionManager, LoginDetailsService loginDetailsService,
			EmpVendorAssociationService empVendorAssociationService,MasterPaymentStatusService masterPaymentStatusService) {
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;
		this.empVendorAssociationService = empVendorAssociationService;
		this.masterPaymentStatusService=masterPaymentStatusService;

	}

	@PostMapping("/payouts")
	public ResponseEntity<ApiResponse<EmpVendorAssociationModel>> makePayment(
			@RequestBody EmpVendorAssociationModel empVendorAssociationModel,
			@RequestHeader("Authorization") String token) {
//System.out.println(empVendorAssociationModel.getAmountPaid());
		HttpStatus httpStatus;
		String message = null;
		EmpVendorAssociationModel paymentDetails = null;
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
			if (loggedUser.getUserType().equals("E")) {
				logger.error("Logged In User is not Admin or Vendor  !!!!!!!!!!!!!!!!!! Throw Exception");
				throw new ApiException("Only Vendor or Admin can make  payment .....!!", 404);
			}

			empVendorAssociationModel = empVendorAssociationService.savePaymentDetails(empVendorAssociationModel);
			if (empVendorAssociationModel == null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			httpStatus = HttpStatus.OK;
			message="Payment Done Successfull !!!!!";

		} else {
			httpStatus = HttpStatus.UNAUTHORIZED;
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 404);
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, empVendorAssociationModel, token));

	}
	
	
	
	@GetMapping("/PaymentStatusList")
	public ResponseEntity<ApiResponse<List<MasterPaymentStatus>>> getAllPaymentStatus() {
//		System.out.println("User status List !!!!!!!!!!!!!!");
		HttpStatus httpStatus = HttpStatus.OK;
		String message = "User Status List Found";

		List<MasterPaymentStatus> paymentStatus = masterPaymentStatusService.getAllPaymentStatuses();
		if (paymentStatus.isEmpty()) {
			httpStatus = HttpStatus.NOT_FOUND;
			message = "No List for Payment Status Found";
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, paymentStatus, null));
	}

}