package com.uchal.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.SessionToken;
import com.uchal.repository.SessionManager;
import com.uchal.service.EmpVendorAssociationService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.MasterPaymentStatusService;

@RestController
@ComponentScan
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })

public class DashboardController {

	private final LoginDetailsService loginDetailsService;
	private final EmpVendorAssociationService empVendorAssociationService;
	private SessionManager sessionManager;
//	private final MasterPaymentStatusService masterPaymentStatusService;
	@Autowired
//	private  final Logger logger = LogManager.getLogger(DashboardController.class);

	public DashboardController(SessionManager sessionManager, LoginDetailsService loginDetailsService,
			EmpVendorAssociationService empVendorAssociationService) {
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;
		this.empVendorAssociationService = empVendorAssociationService;
//		this.masterPaymentStatusService = masterPaymentStatusService;

	}

	// Under How many vendors employee have worked.
	@GetMapping("/vendorCountEmployee")
	public ResponseEntity<ApiResponse<Long>> getVendorCount(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		UserDetails loggedUser = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session == null)

				throw new ApiException("Invalid Session ", 404);
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

		}
		if (token == null)

			throw new ApiException("Invalid Token ", 404);
		long count = empVendorAssociationService.getVendorCount(loggedUser.getUserId());

//		if (count ==0)
//			throw new ApiException("", 404);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, count, token));

	}

	// out of All vendors worked(Employee worked under) , how many tasks are
	// completed, Aborted, Open.
	@GetMapping("/statusCountEmployee")
	public ResponseEntity<ApiResponse<List<Object[]>>> getStatusCount(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		UserDetails loggedUser = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session == null)

				throw new ApiException("Invalid Session ", 404);
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

		}
		if (token == null)

			throw new ApiException("Invalid Token ", 404);
		List<Object[]> count = empVendorAssociationService.getStatusCount(loggedUser.getUserId());

//		if (count ==0)
//			throw new ApiException("", 404);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, count, token));

	}

	// Task completion ratio
	@GetMapping("/taskCompletionPercentageEmployee")
	public ResponseEntity<ApiResponse<Double>> getTaskCompletionPercentage(
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		UserDetails loggedUser = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session == null)

				throw new ApiException("Invalid Session ", 404);
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

		}
		if (token == null)

			throw new ApiException("Invalid Token ", 404);
		double percent = empVendorAssociationService.getTaskCompletionPercentage(loggedUser.getUserId());
		System.out.println(percent);

//			if (count ==0)
//				throw new ApiException("", 404);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, percent, token));

	}

	// Vendor Dasboard API
	// How many employees are working under specific Vendor

	@GetMapping("/EmployeeCountVendor")
	public ResponseEntity<ApiResponse<Long>> getEmployeeCount(@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		UserDetails loggedUser = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session == null)

				throw new ApiException("Invalid Session ", 404);
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

		}
		if (token == null)

			throw new ApiException("Invalid Token ", 404);
		long count = empVendorAssociationService.getEmployeeCount(loggedUser.getUserId());

//			if (count ==0)
//				throw new ApiException("", 404);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, count, token));

	}

	// out of All alloted employees , how many has
	// completed, Aborted, Open tasks
	@GetMapping("/statusCountByVendor")
		public ResponseEntity<ApiResponse<List<Object[]>>> getStatusCountbyVendor(
				@RequestHeader("Authorization") String token) {
			HttpStatus httpStatus;
			String message = null;
			UserDetails loggedUser = null;
			if (token != null) {
				String sessionToken = token.substring(7); // Remove "Bearer " prefix
				SessionToken session = sessionManager.getSessionToken(sessionToken);
				if (session == null)

					throw new ApiException("Invalid Session ", 404);
				loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

			}
			if (token == null)

				throw new ApiException("Invalid Token ", 404);
			List<Object[]> count = empVendorAssociationService.getStatusCountbyVendor(loggedUser.getUserId());

//			if (count ==0)
//				throw new ApiException("", 404);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, count, token));

		}

	// Admin Dashboard APIs

	// out of All alloted employees , how many has
	// completed, Aborted, Open tasks
	@GetMapping("/statusCountByVendorforAdmin{id}")
				public ResponseEntity<ApiResponse<List<Object[]>>> getStatusCountbyVendorforAdmin(@PathVariable("id") int id,
						@RequestHeader("Authorization") String token) {
					HttpStatus httpStatus;
					String message = null;
					UserDetails loggedUser = null;
					if (token != null) {
						String sessionToken = token.substring(7); // Remove "Bearer " prefix
						SessionToken session = sessionManager.getSessionToken(sessionToken);
						if (session == null)

							throw new ApiException("Invalid Session ", 404);
						loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

					}
					if (token == null)

						throw new ApiException("Invalid Token ", 404);
					List<Object[]> count=null;
					if (id==0)
						count = empVendorAssociationService.getStatusCountbyVendor(id);
					else
						count = empVendorAssociationService.getStatusCountbyAllVendor();


//					if (count ==0)
//						throw new ApiException("", 404);

					return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, count, token));

				}
	// Task completion ratio
		@GetMapping("/taskCompletionPercentageEmployeeforAdmin{id}")
		public ResponseEntity<ApiResponse<Double>> getTaskCompletionPercentageForAdmin(@PathVariable("id") int id,
				@RequestHeader("Authorization") String token) {
			HttpStatus httpStatus;
			String message = null;
			UserDetails loggedUser = null;
			if (token != null) {   
				String sessionToken = token.substring(7); // Remove "Bearer " prefix
				SessionToken session = sessionManager.getSessionToken(sessionToken);
				if (session == null)

					throw new ApiException("Invalid Session ", 404);
				loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();

			}
			if (token == null)

				throw new ApiException("Invalid Token ", 404);
			double percent=0;
			if (id==0)
			percent=empVendorAssociationService.getTaskCompletionPercentagefrAllEmployee();
			else
			percent= empVendorAssociationService.getTaskCompletionPercentage(id);

//				if (count ==0)
//					throw new ApiException("", 404);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, percent, token));

		}


				
				
		
		         
		
}
