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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.MasterPaymentStatus;
import com.uchal.entity.MasterUserStatus;
import com.uchal.entity.MasterUserType;
import com.uchal.model.ApiResponse;
import com.uchal.repository.SessionManager;
import com.uchal.service.MasterUserTypeService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UserTypeController {
	
	private final MasterUserTypeService masterUserTypeService;

	private SessionManager sessionManager;
	@Autowired
	private static final Logger logger = LogManager.getLogger(UserTypeController.class);
	public UserTypeController(SessionManager sessionManager,MasterUserTypeService masterUserTypeService) {
		this.sessionManager = sessionManager;
		this.masterUserTypeService=masterUserTypeService;
		

	}
	
	@GetMapping("/userTypeList")
	public ResponseEntity<ApiResponse<List<MasterUserType>>> getAllPaymentStatus() {
//		System.out.println("User status List !!!!!!!!!!!!!!");
		HttpStatus httpStatus = HttpStatus.OK;
		String message = "Payment Status List Found";

		List<MasterUserType> userType = masterUserTypeService.getAllMasterUserTypes();
		if (userType.isEmpty()) {
			httpStatus = HttpStatus.NOT_FOUND;
			message = "No List for Payment Status Found";
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userType, null));
	

}
}
