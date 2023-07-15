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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.MasterPaymentStatus;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.EmpVendorAssociationModel;
import com.uchal.model.PaymentDetailsModel;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.EmpVendorAssociationService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.MasterPaymentStatusService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })

public class PaymentController {
	private final LoginDetailsService loginDetailsService;
	private final EmpVendorAssociationService empVendorAssociationService;
	private SessionManager sessionManager;
	private final MasterPaymentStatusService masterPaymentStatusService;
	@Autowired
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	public PaymentController(SessionManager sessionManager, LoginDetailsService loginDetailsService,
			EmpVendorAssociationService empVendorAssociationService,
			MasterPaymentStatusService masterPaymentStatusService) {
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;
		this.empVendorAssociationService = empVendorAssociationService;
		this.masterPaymentStatusService = masterPaymentStatusService;

	}

	@PostMapping("/payment")
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
		System.out.println(sessionToken);

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
			empVendorAssociationModel.setVendorId(loggedUser.getUserId());
			empVendorAssociationModel = empVendorAssociationService.savePaymentDetails(empVendorAssociationModel);
			if (empVendorAssociationModel == null) {
				throw new ApiException("Issue While saving data !!", 404);
			}
			httpStatus = HttpStatus.OK;
			message = "Payment Done Successfull !!!!!";

		} else {
//			httpStatus = HttpStatus.UNAUTHORIZED;
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 404);
		}
		return ResponseEntity.status(httpStatus)
				.body(new ApiResponse<>(httpStatus, message, empVendorAssociationModel, token));

	}

	@GetMapping("/PaymentStatusList")
	public ResponseEntity<ApiResponse<List<MasterPaymentStatus>>> getAllPaymentStatus() {
//		System.out.println("User status List !!!!!!!!!!!!!!");
		HttpStatus httpStatus = HttpStatus.OK;
		String message = "Payment Status List Found";

		List<MasterPaymentStatus> paymentStatus = masterPaymentStatusService.getAllPaymentStatuses();
		if (paymentStatus.isEmpty()) {
			httpStatus = HttpStatus.NOT_FOUND;
			message = "No List for Payment Status Found";
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, paymentStatus, null));
	}

	@GetMapping("/getAllPayment")
	public ResponseEntity<ApiResponse<List<PaymentDetailsModel>>> getAllPayment(
			@RequestHeader("Authorization") String token) {
//		System.out.println("User status List !!!!!!!!!!!!!!");

		HttpStatus httpStatus = HttpStatus.OK;
		String message = null;
		EmpVendorAssociationModel paymentDetails = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		UserDetails loggedUser = null;
		List<PaymentDetailsModel> paymentList = null;
		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E")) {
				logger.error("Logged In User is not Admin or Vendor  !!!!!!!!!!!!!!!!!! Throw Exception");
				throw new ApiException("Only Vendor or Admin can veiw All   payment .....!!", 404);
			}
		}

//		HttpStatus httpStatus = HttpStatus.OK;
//		String message = "Lis of All Payment  Found";
		if (loggedUser != null)
			paymentList = empVendorAssociationService.setpaymentModel(loggedUser.getUserId());
		if (paymentList == null) {
			httpStatus = HttpStatus.NOT_FOUND;
			message = "No List for Payment  Found";
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, paymentList, null));

	}

	@PutMapping("/updatePayment")
	public ResponseEntity<ApiResponse<EmpVendorAssociationModel>> updatePayment(
			@RequestBody EmpVendorAssociationModel empVendorAssociationModel,
			@RequestHeader("Authorization") String token) {
//System.out.println(empVendorAssociationModel.getAmountPaid());
		HttpStatus httpStatus;
		String message = null;
		EmpVendorAssociationModel paymentDetails = null;
		UserDetailsModel user = null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		System.out.println(sessionToken);

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
			empVendorAssociationModel.setVendorId(loggedUser.getUserId());
			empVendorAssociationModel = empVendorAssociationService.savePaymentDetails(empVendorAssociationModel);
			if (empVendorAssociationModel == null) {
				throw new ApiException("Issue While saving data !!", 404);
			}
			httpStatus = HttpStatus.OK;
			message = "Payment updated  Successfull !!!!!";

		} else {
//			httpStatus = HttpStatus.UNAUTHORIZED;
			// Invalid session token or unauthorized access
			throw new ApiException("Invalid session token or unauthorized access", 404);
		}
		return ResponseEntity.status(httpStatus)
				.body(new ApiResponse<>(httpStatus, message, empVendorAssociationModel, token));

	}

}