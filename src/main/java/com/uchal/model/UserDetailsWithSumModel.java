package com.uchal.model;

import java.math.BigDecimal;

import com.uchal.entity.UserDetails;

public class UserDetailsWithSumModel {
	private String name;
	private BigDecimal totalAmountPaid;
	private String userTpe;
	private int userId;
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
	 * @return the totalAmountPaid
	 */
	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}
	/**
	 * @param totalAmountPaid the totalAmountPaid to set
	 */
	public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}
	/**
	 * @return the userTpe
	 */
	public String getUserTpe() {
		return userTpe;
	}
	/**
	 * @param userTpe the userTpe to set
	 */
	public void setUserTpe(String userTpe) {
		this.userTpe = userTpe;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	

}
