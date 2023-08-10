package com.uchal.model;

import com.uchal.entity.FIRDetails;

public class FIRDetailsModel {

	private FIRDetails FIRDetails;
	private String name;
	private Long mobileNumber;
	private String userType;
	/**
	 * @return the fIRDetails
	 */
	public FIRDetails getFIRDetails() {
		return FIRDetails;
	}
	/**
	 * @param fIRDetails the fIRDetails to set
	 */
	public void setFIRDetails(FIRDetails fIRDetails) {
		FIRDetails = fIRDetails;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the mobileNumber
	 */
	public Long getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	

}
