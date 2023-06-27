package com.uchal.model;

public class LoginDetailsModel {

	private String username;
	private String password;
	private int user_id;
	private int user_status;
	private int login_attepmts;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_status() {
		return user_status;
	}
	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}
	public int getLogin_attepmts() {
		return login_attepmts;
	}
	public void setLogin_attepmts(int login_attepmts) {
		this.login_attepmts = login_attepmts;
	}
	

}
