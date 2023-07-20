package com.uchal.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.uchal.entity.AddDetailsEntity;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.SessionToken;
import com.uchal.model.UserDetailsModel;
import com.uchal.repository.SessionManager;
import com.uchal.service.AddDetailsEntityService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.UserDetailsService;

import java.util.List;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/adds")
public class AddDetailsEntityController {

	private final AddDetailsEntityService addDetailsEntityService;
	private final LoginDetailsService loginDetailsService;

	private final SessionManager sessionManager;
	@Autowired
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	public AddDetailsEntityController(AddDetailsEntityService addDetailsEntityService,
			LoginDetailsService loginDetailsService, SessionManager sessionManager) {
		this.addDetailsEntityService = addDetailsEntityService;
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;

	}

	@PostMapping("/Save")
	public ResponseEntity<ApiResponse<AddDetailsEntity>> addAddDetailsEntity(
			@RequestBody AddDetailsEntity addDetailsEntity, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		String message = null;
		if (token != null) {
			String sessionToken = token.substring(7); // Remove "Bearer " prefix
			SessionToken session = sessionManager.getSessionToken(sessionToken);
			if (session != null) {
				UserDetails loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
				System.out.println(session.getUserId());
				System.out.println(loggedUser.getUserType());
				if (!loggedUser.getUserType().equals("A"))

					throw new ApiException("Only  Admin can  Save Adds !!", 404);

				addDetailsEntity.setCreatedBy(loggedUser.getUserId());
			}
		}
		AddDetailsEntity savedEntity = addDetailsEntityService.saveAddDetails(addDetailsEntity);
		if (savedEntity == null)
			throw new ApiException("Error while Saving  Adds !!", 404);
		message = "Adds Saved Successfully";

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, savedEntity, token));
	}

	@GetMapping("/AllAvailableAdds")
	public ResponseEntity<ApiResponse<List<AddDetailsEntity>>> getAllAddDetailsEntities() {
		String message =null;
		List<AddDetailsEntity> entities = addDetailsEntityService.getAvailableAdds();
		if (entities==null)
			throw new ApiException("Adds are not found", 404);

		message="Adds Found";
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK, message, entities, null));
	}
}
