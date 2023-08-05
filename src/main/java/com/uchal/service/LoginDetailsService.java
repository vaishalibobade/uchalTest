package com.uchal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.LoginDetails;
import com.uchal.model.ApiException;
import com.uchal.model.ChangePassword;
import com.uchal.repository.LoginDetailsRepository;

@Service
public class LoginDetailsService {
	private final LoginDetailsRepository loginDetailsRepository;
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	public LoginDetailsService(LoginDetailsRepository loginDetailsRepository) {
		this.loginDetailsRepository = loginDetailsRepository;
	}

	public LoginDetails saveLoginDetails(LoginDetails loginDetails) {
		return loginDetailsRepository.save(loginDetails);
	}

	public LoginDetails getLoginDetailsById(int loginDtlId) {
		return loginDetailsRepository.findById(loginDtlId).orElse(null);
	}

	public List<LoginDetails> getAllLoginDetails() {
		return loginDetailsRepository.findAll();
	}

	public void deleteLoginDetails(int loginDtlId) {
		loginDetailsRepository.deleteById(loginDtlId);
	}

	// Other methods as needed
	public LoginDetails getByUsername(String username) {
		LoginDetails user = null;
		try {
			user = loginDetailsRepository.findByUsername(username);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return user;
	}

	public LoginDetails updateLoginAttempts(String username) {

		LoginDetails loginDetails = getByUsername(username);
		try {

			loginDetails.setLoginAttempts(loginDetails.getLoginAttempts() + 1); // increament login attempt by 1
			if (loginDetails.getLoginAttempts() == 3) {
				loginDetails.setUserStatus("L");
			}
			saveLoginDetails(loginDetails);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return loginDetails;

	}

	public boolean validateUserToLogin(LoginDetails loginDetails) {
		if (loginDetails.getUserStatus().trim().equals("A")) {
			return true;
		}
		return false;
	}

	public LoginDetails setLoggedInUser(LoginDetails loginDetails) {
		loginDetails.setLoginAttempts(0); // reset count of login attemps
		saveLoginDetails(loginDetails);
		return loginDetails;
	}

	public LoginDetails setUserStatus(int loggedUID, int unlockUID, String status) {
		LoginDetails details = null;
		Optional<LoginDetails> detailslist = loginDetailsRepository.findByUserDetailsUserId(unlockUID);
		if (!detailslist.isPresent()) {
			logger.error("User not Found  for unlock!!", unlockUID);
			throw new ApiException("User Not Found !!", 404);

		}
		details = detailslist.get();
		details.setUserStatus(status);
		details.setUpdatedBy(loggedUID);
		details.setUpdatedOn(LocalDateTime.now());
		if (status.equals("A")) {
			details.setLoginAttempts(0);
		}
		saveLoginDetails(details);

		return details;
	}

	public String getUserStatus(int id) {
		return loginDetailsRepository.findByUserDetailsUserId(id).get().getUserStatus();
	}

	public boolean checkUsernameExist(String username) {

		if (loginDetailsRepository.findAllByUsername(username).isEmpty()) {
			return false;
		}
		return true;
	}

	public LoginDetails updatePassword(ChangePassword changePassword) {
		LoginDetails loginDetails = loginDetailsRepository.findByUsername(changePassword.getUserName());

		loginDetails.setPassword(changePassword.getPassword());
		loginDetailsRepository.save(loginDetails);

		return loginDetails;

	}

}
