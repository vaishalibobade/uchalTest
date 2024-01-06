package com.uchal.model;

import java.time.LocalDateTime;

public class UnBlockRequestModel {
	private int requestId;
	private int userToUnBlock;
	private String userToUnBlockName;
	private int requestedBy;
	private String remark;
	private String requestStatus;
	private int createdBy;
	private LocalDateTime createdOn;
	
	
	public UnBlockRequestModel(
            int requestId,
            int userToUnBlock,
            String userToUnBlockName,
            int requestedBy,
            String remark,
            String requestStatus,
            int createdBy,
            LocalDateTime createdOn
    ) {
        this.requestId = requestId;
        this.userToUnBlock = userToUnBlock;
        this.userToUnBlockName = userToUnBlockName;
        this.requestedBy = requestedBy;
        this.remark = remark;
        this.requestStatus = requestStatus;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }


	public int getRequestId() {
		return requestId;
	}


	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}


	public int getUserToUnBlock() {
		return userToUnBlock;
	}


	public void setUserToUnBlock(int userToUnBlock) {
		this.userToUnBlock = userToUnBlock;
	}


	public String getUserToUnBlockName() {
		return userToUnBlockName;
	}


	public void setUserToUnBlockName(String userToUnBlockName) {
		this.userToUnBlockName = userToUnBlockName;
	}


	public int getRequestedBy() {
		return requestedBy;
	}


	public void setRequestedBy(int requestedBy) {
		this.requestedBy = requestedBy;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRequestStatus() {
		return requestStatus;
	}


	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}


	public int getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	
}