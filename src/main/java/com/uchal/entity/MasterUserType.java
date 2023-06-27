package com.uchal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "master_usertype")
public class MasterUserType {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  private String user_type;
  
  private String abreviation;
  
  @ManyToOne
  @JoinColumn(name = "user_belongTo")
  private MasterUserStatus user_belongTo;

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
    return user_type;
  }

  public void setUserType(String user_type) {
    this.user_type = user_type;
  }

  public String getAbreviation() {
    return abreviation;
  }

  public void setAbbreviation(String abreviation) {
    this.abreviation = abreviation;
  }

  public MasterUserStatus getUserBelongTo() {
    return user_belongTo;
  }

  public void setUserBelongTo(MasterUserStatus user_belongTo) {
    this.user_belongTo = user_belongTo;
  }
}
