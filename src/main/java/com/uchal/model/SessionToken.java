package com.uchal.model;

public class SessionToken {
    private String token;
    private String userId;
    // Add additional fields as needed

    public SessionToken(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }
}

