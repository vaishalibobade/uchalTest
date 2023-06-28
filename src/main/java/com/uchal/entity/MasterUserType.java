package com.uchal.entity;

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
