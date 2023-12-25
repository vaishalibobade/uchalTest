package com.uchal.model;

import java.time.LocalDateTime;

public class BlockRequestModel {
	private int requestId;
	private int userToBlock;
	private String userToBlockName;
	private int requestedBy;
	private String remark;
	private String requestStatus;
	private int createdBy;
	private LocalDateTime createdOn;
	
	
	public BlockRequestModel(
            int requestId,
            int userToBlock,
            String userToBlockName,
            int requestedBy,
            String remark,
            String requestStatus,
            int createdBy,
            LocalDateTime createdOn
    ) {
        this.requestId = requestId;
        this.userToBlock = userToBlock;
        this.userToBlockName = userToBlockName;
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
	public int getUserToBlock() {
		return userToBlock;
	}
	public void setUserToBlock(int userToBlock) {
		this.userToBlock = userToBlock;
	}
	public String getUserToBlockName() {
		return userToBlockName;
	}
	public void setUserToBlockName(String userToBlockName) {
		this.userToBlockName = userToBlockName;
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
