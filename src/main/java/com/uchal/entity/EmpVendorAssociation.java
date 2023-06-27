package com.uchal.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empvendor_association")
public class EmpVendorAssociation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;


  private int vendorId;

  private int employeeId;

  private int amount_paid;
  private int duration_days;

  @ManyToOne
  @JoinColumn(name = "user_status", nullable = false)
  private MasterUserStatus userStatus;

  
  private int createdBy;

  private LocalDateTime createdOn;


  private int updatedBy;

  private LocalDateTime updatedOn;

  private Date start_date;
  private Date end_date;

  @OneToOne
  @JoinColumn(name = "payment_status")
  private MasterPaymentStatus paymentStatus;
  
  // Default constructor, getters, and setters

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getVendorId() {
	return vendorId;
}

public void setVendorId(int vendorId) {
	this.vendorId = vendorId;
}

public int getEmployeeId() {
	return employeeId;
}

public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
}

public int getAmount_paid() {
	return amount_paid;
}

public void setAmount_paid(int amount_paid) {
	this.amount_paid = amount_paid;
}

public int getDuration_days() {
	return duration_days;
}

public void setDuration_days(int duration_days) {
	this.duration_days = duration_days;
}

public MasterUserStatus getUserStatus() {
	return userStatus;
}

public void setUserStatus(MasterUserStatus userStatus) {
	this.userStatus = userStatus;
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

public int getUpdatedBy() {
	return updatedBy;
}

public void setUpdatedBy(int updatedBy) {
	this.updatedBy = updatedBy;
}

public LocalDateTime getUpdatedOn() {
	return updatedOn;
}

public void setUpdatedOn(LocalDateTime updatedOn) {
	this.updatedOn = updatedOn;
}

public Date getStart_date() {
	return start_date;
}

public void setStart_date(Date start_date) {
	this.start_date = start_date;
}

public Date getEnd_date() {
	return end_date;
}

public void setEnd_date(Date end_date) {
	this.end_date = end_date;
}

public MasterPaymentStatus getPaymentStatus() {
	return paymentStatus;
}

public void setPaymentStatus(MasterPaymentStatus paymentStatus) {
	this.paymentStatus = paymentStatus;
}



  
}

