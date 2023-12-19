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

import com.uchal.entity.RegistrationPayment;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.RegistrationPaymentModel;
import com.uchal.model.SessionToken;
import com.uchal.repository.SessionManager;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.RegistrationPaymentService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class RegistrationPaymentController {

	private RegistrationPaymentService registrationPaymentService;
	private SessionManager sessionManager;
	private final LoginDetailsService loginDetailsService;

	@Autowired
	public RegistrationPaymentController(RegistrationPaymentService registrationPaymentService,
			SessionManager sessionManager, LoginDetailsService loginDetailsService) {
		this.registrationPaymentService = registrationPaymentService;
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;
	}

	@PostMapping("/MakeRegistrationPayment")
	public ResponseEntity<ApiResponse<RegistrationPayment>> addPayment(
			@RequestBody RegistrationPaymentModel payment, @RequestHeader("Authorization") String token) {

		HttpStatus httpStatus;
		String message = null;
		RegistrationPayment paid=null;
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		System.out.println(sessionToken);

		if (session != null) {
			// User is authenticated, process the protected resource request
//        @SuppressWarnings("removal")
//        return "Protected Resource for User: " + userId;
			UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			if (loggedUser == null) {
				throw new ApiException("Invalid Session", 401);
			}

			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			payment.setUserId(loggedUser.getUserId());
			 paid = registrationPaymentService.addPayment(payment);
			if (paid == null) {
				throw new ApiException("Issue while payment ", 401);
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(HttpStatus.OK, "Payment done successfully ", paid, token));	}

//	@GetMapping
//	public ResponseEntity<List<RegistrationPayment>> getAllPayments() {
//		List<RegistrationPayment> payments = registrationPaymentService.getAllPayments();
//		return new ResponseEntity<>(payments, HttpStatus.OK);
//	}
//
//	@GetMapping("/{paymentId}")
//	public ResponseEntity<RegistrationPayment> getPaymentById(@PathVariable int paymentId) {
//		RegistrationPayment payment = registrationPaymentService.getPaymentById(paymentId);
//		if (payment != null) {
//			return new ResponseEntity<>(payment, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@PutMapping("/{paymentId}")
//	public ResponseEntity<Void> updatePayment(@PathVariable int paymentId, @RequestBody RegistrationPayment payment) {
//		RegistrationPayment existingPayment = registrationPaymentService.getPaymentById(paymentId);
//		if (existingPayment != null) {
//			payment.setPaymentId(paymentId);
//			registrationPaymentService.updatePayment(payment);
//			return new ResponseEntity<>(HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@DeleteMapping("/{paymentId}")
//	public ResponseEntity<Void> deletePayment(@PathVariable int paymentId) {
//		RegistrationPayment existingPayment = registrationPaymentService.getPaymentById(paymentId);
//		if (existingPayment != null) {
//			registrationPaymentService.deletePayment(paymentId);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
}
