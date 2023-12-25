package com.uchal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uchal.entity.BlockRequestEntity;
import com.uchal.entity.UserDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ApiResponse;
import com.uchal.model.BlockRequestModel;
import com.uchal.model.SessionToken;
import com.uchal.repository.SessionManager;
import com.uchal.service.BlockRequestService;
import com.uchal.service.LoginDetailsService;
import com.uchal.service.UserDetailsService;

@RestController
@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })

public class BlockRequestController {

	private final BlockRequestService blockRequestService;
	private final UserDetailsService userDetailsService;
	private final LoginDetailsService loginDetailsService;
	private SessionManager sessionManager;

	@Autowired
	public BlockRequestController(BlockRequestService blockRequestService, UserDetailsService userDetailsService,
			LoginDetailsService loginDetailsService, SessionManager sessionManager) {
		this.blockRequestService = blockRequestService;
		this.userDetailsService = userDetailsService;
		this.sessionManager = sessionManager;
		this.loginDetailsService = loginDetailsService;
	}

	@GetMapping("/allBlockRequest")
	public ResponseEntity<ApiResponse<List<BlockRequestEntity>>> getAllBlockRequests(
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//		System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException("Only Vendor/ Admin can view Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}

		List<BlockRequestEntity> blockRequests = blockRequestService.getAllBlockRequests();
//    	httpStatus.OK
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(HttpStatus.OK, message, blockRequests, token));

	}

	@GetMapping("/allActiveBlockRequest")
	public ResponseEntity<ApiResponse<List<BlockRequestModel>>> getAllActiveBlockRequests(
			@RequestHeader("Authorization") String token) {
//    public ResponseEntity<List<BlockRequestEntity>> getAllActiveBlockRequests() {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//		System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException("Only Vendor/ Admin can view Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		List<BlockRequestModel> blockRequests = blockRequestService.getAllActiveBlockRequests();
//        return new ResponseEntity<>(blockRequests, HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(HttpStatus.OK, message, blockRequests, token));

	}

	@GetMapping("/getblockRequest/{requestId}")

	public ResponseEntity<BlockRequestEntity> getBlockRequestById(@PathVariable int requestId,
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//		System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());
			if (loggedUser.getUserType().equals("E"))

				throw new ApiException("Only Vendor/ Admin can view Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);
		}

		return blockRequestService.getBlockRequestById(requestId)
				.map(blockRequest -> new ResponseEntity<>(blockRequest, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/generateRequest")
	public ResponseEntity<BlockRequestEntity> createBlockRequest(@RequestBody BlockRequestEntity blockRequest,
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//	System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			if (!(loggedUser.getUserType().equals("V") || loggedUser.getUserType().equals("S")))

				throw new ApiException("Only Vendor/ Subvendor can generate Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		blockRequest.setRequestedBy(loggedUser.getUserId());
		if (!userDetailsService.checkRegistrationUnder(blockRequest.getUserToBlock(), blockRequest.getRequestedBy()))
			throw new ApiException("Requested Employee is not registered under you", 402);
		BlockRequestEntity createdBlockRequest = blockRequestService.createBlockRequest(blockRequest);
		return new ResponseEntity<>(createdBlockRequest, HttpStatus.CREATED);
	}

	@PutMapping("/updateblockRequest/{requestId}")
	public ResponseEntity<BlockRequestEntity> updateBlockRequest(@PathVariable int requestId,
			@RequestBody BlockRequestEntity updatedBlockRequest, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//    	System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			if (!loggedUser.getUserType().equals("A"))

				throw new ApiException("Admin can update Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		BlockRequestEntity updatedEntity = blockRequestService.updateBlockRequest(requestId, updatedBlockRequest);

		if (updatedEntity != null) {
			return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteRequest/{requestId}")
	public ResponseEntity<Void> deleteBlockRequest(@PathVariable int requestId,
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		String message = null;
//    	System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			if (!loggedUser.getUserType().equals("A"))

				throw new ApiException("Only Admin can delete Block Request !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		blockRequestService.deleteBlockRequest(requestId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/blockUser/{userId}")
	public ResponseEntity<ApiResponse<BlockRequestEntity>> unblockUser(@PathVariable int userId,
			@RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		UserDetails updatedUser = null;

		String message = null;
//    	System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			if (!loggedUser.getUserType().equals("A"))

				throw new ApiException("Only Admin can Block User !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		if (userDetailsService.getUserDetailsById(userId) == null)
			throw new ApiException("User requested to Block is not found !!!", 401);
		if (blockRequestService.blockUser(userId)) {
			updatedUser = userDetailsService.updateUserStatus(userId, 5);
		}
		if (updatedUser != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(HttpStatus.OK, "User Blocked", null, token));
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/unblockUser/{userId}")
	public ResponseEntity<ApiResponse<BlockRequestEntity>> blockUser(@PathVariable int userId,
			@RequestBody BlockRequestEntity updatedBlockRequest, @RequestHeader("Authorization") String token) {
		HttpStatus httpStatus;
		UserDetails loggedUser = null;
		UserDetails updatedUser = null;

		String message = null;
//    	System.out.println("in update");
		String sessionToken = token.substring(7); // Remove "Bearer " prefix
		SessionToken session = sessionManager.getSessionToken(sessionToken);
		if (session != null) {
			loggedUser = loginDetailsService.getByUsername(session.getUserId()).getUserDetails();
			System.out.println(session.getUserId());
			System.out.println(loggedUser.getUserType());

			if (!loggedUser.getUserType().equals("A"))

				throw new ApiException("Admin can  UnBlock User !!", 400);

		} else {
			throw new ApiException("Session cannot be null !!!!!!", 401);

		}
		if (userDetailsService.getUserDetailsById(userId) == null)
			throw new ApiException("User requested to Block is not found !!!", 401);
		if (blockRequestService.blockUser(userId)) {
			updatedUser = userDetailsService.updateUserStatus(userId, 2);
		}
		if (updatedUser != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(HttpStatus.OK, "User unblocked", null, token));
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
