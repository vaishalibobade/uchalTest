package com.uchal.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.uchal.model.SessionToken;
import com.uchal.repository.SessionManager;

import org.springframework.stereotype.Component;

@Component
public class DefaultSessionManager implements SessionManager {
    private Map<String, SessionToken> sessionTokens;

    public DefaultSessionManager() {
        sessionTokens = new HashMap<>();
    }

    @Override
    public SessionToken createSession(String userId) {
        String token = generateSessionToken();
        SessionToken sessionToken = new SessionToken(token, userId);
        sessionTokens.put(token, sessionToken);
        return sessionToken;
    }

    @Override
    public SessionToken getSessionToken(String token) {
        return sessionTokens.get(token);
    }

    @Override
    public boolean removeSession(String token) {
        sessionTokens.remove(token);
        return true;
    }

    private String generateSessionToken() {
        // Generate a session token
        return UUID.randomUUID().toString();
    }
}
