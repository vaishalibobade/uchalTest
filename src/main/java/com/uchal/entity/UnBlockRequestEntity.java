package com.uchal.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "unblock_request")
public class UnBlockRequestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int requestId;
	
	private int userToUnBlock;
	private String userToUnBlockName;
	private int requestedBy;
	private String remark;
	private String requestStatus;
	private int createdBy;
	private LocalDateTime createdOn;
	private Integer updatedBy;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
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
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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
	
}
