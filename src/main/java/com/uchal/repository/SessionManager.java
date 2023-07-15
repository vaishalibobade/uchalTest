package com.uchal.repository;

import com.uchal.model.SessionToken;

public interface SessionManager {
    // Define session management methods
    SessionToken createSession(String userId);
    SessionToken getSessionToken(String token);
    boolean removeSession(String token);
}

