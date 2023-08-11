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

import com.uchal.entity.MasterUserStatus;
import com.uchal.model.ApiResponse;
import com.uchal.service.MasterUserStatusService;

@RestController
@Component
@CrossOrigin(origins = "*",allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class UserStatusController {
	private final MasterUserStatusService masterUserStatusService;

	@Autowired
	private static final Logger logger = LogManager.getLogger(UserStatusController.class);

	public UserStatusController(MasterUserStatusService masterUserStatusService) {
		this.masterUserStatusService = masterUserStatusService;

	}


	
	
	@GetMapping("/UserStatusList")
	public ResponseEntity<ApiResponse<List<MasterUserStatus>>> getAllUserStatus() {
		System.out.println("User status List !!!!!!!!!!!!!!");
		HttpStatus httpStatus = HttpStatus.OK;
		String message = "User Status List Found";

		List<MasterUserStatus> userStatus = masterUserStatusService.getAllMasterUserStatuses();
		if (userStatus.isEmpty()) {
			httpStatus = HttpStatus.NOT_FOUND;
			message = "NO List for User Status Found";
		}
		return ResponseEntity.status(httpStatus).body(new ApiResponse<>(httpStatus, message, userStatus, null));
	}
}
