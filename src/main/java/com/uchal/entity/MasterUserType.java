package com.uchal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "master_usertype")
public class MasterUserType {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(name = "user_type")
  private String userType;
  
  private String abreviation;
  
  @Column(name = "user_belongTo")
  private String userBelongTo;

  // Default constructor, getters, and setters

  public MasterUserType() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String user_type) {
    this.userType = user_type;
  }

  public String getAbreviation() {
    return abreviation;
  }

  public void setAbbreviation(String abreviation) {
    this.abreviation = abreviation;
  }

  public String getUserBelongTo() {
    return userBelongTo;
  }

  public void setUserBelongTo(String user_belongTo) {
    this.userBelongTo = user_belongTo;
  }
}
