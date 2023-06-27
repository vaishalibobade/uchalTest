package com.uchal.model;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private String token;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data,String token) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
        this.setToken(token);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token2) {
		this.token = token2;
	}

   
    
    
}

