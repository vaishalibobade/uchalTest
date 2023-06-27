package com.uchal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_paymentstatus")
public class MasterPaymentStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String status;

  // Default constructor, getters, and setters

  public MasterPaymentStatus() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
