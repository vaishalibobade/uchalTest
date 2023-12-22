package com.uchal.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "block_request")

public class BlockRequestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requestId;
	
	private int userToBlock;
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
	public int getUserToBlock() {
		return userToBlock;
	}
	public void setUserToBlock(int userToBlock) {
		this.userToBlock = userToBlock;
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
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	private LocalDateTime updatedOn;

}
