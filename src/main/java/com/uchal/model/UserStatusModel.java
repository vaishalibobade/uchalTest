package com.uchal.model;

import java.sql.Date;

public class UserStatusModel {
	
	private int userId;
	private int currentStatusId;
	private int updatedBy;
	private Date updateOn;
	private String remarks;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCurrentStatusId() {
		return currentStatusId;
	}
	public void setCurrentStatusId(int currentStatusId) {
		this.currentStatusId = currentStatusId;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdateOn() {
	
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
