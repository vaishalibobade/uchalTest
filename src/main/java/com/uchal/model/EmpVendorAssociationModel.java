package com.uchal.model;

import java.math.BigDecimal;
import java.sql.Date;

public class EmpVendorAssociationModel {
 
	private int id;
	private int userId;
	private BigDecimal amountPaid;
	private int durationDays;
	private int userStatus;
	private int paymentstatus;
	private Date startDate;
	private Date endDate;
	private int vendorId;
	public int getUserId() { 
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDurationDays() {
		return durationDays;
	}
	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	public int getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(int paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getAmountPaid() {
	    return amountPaid; // Assuming amount_paid is an integer or another numeric type
	  }

	public void setAmountPaid(BigDecimal amount_paid) {
		this.amountPaid = amount_paid;
	}
	
}
