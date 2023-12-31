package com.uchal.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "master_paymentstatus")
public class MasterPaymentStatus {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String status;

  @OneToMany(mappedBy = "paymentStatus")
  private List<EmpVendorAssociation> empVendorAssociations;

  // Constructors, getters, and setters


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

