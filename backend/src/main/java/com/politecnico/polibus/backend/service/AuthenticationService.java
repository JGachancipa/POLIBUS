package com.politecnico.polibus.backend.service;

public interface AuthenticationService {
    boolean isValidCredentials(String username, String encodePass);
}
