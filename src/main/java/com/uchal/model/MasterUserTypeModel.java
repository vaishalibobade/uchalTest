package com.uchal.model;

public class MasterUserTypeModel {
	 private int id;
	  
	  private String user_type;
	  
	  private String abreviation;
	  
	  public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}

	public String getUser_belongTo() {
		return user_belongTo;
	}

	public void setUser_belongTo(String user_belongTo) {
		this.user_belongTo = user_belongTo;
	}

	private String user_belongTo;
}
