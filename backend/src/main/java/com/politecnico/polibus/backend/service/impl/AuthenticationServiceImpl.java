package com.politecnico.polibus.backend.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.politecnico.polibus.backend.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${auth.username}")
    private String VALID_USERNAME;

    @Value("${auth.password}")
    private String VALID_PASSWORD;

    @Override
    public boolean isValidCredentials(String username, String password) {
        String decodePass = decodeBase64(password);
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(decodePass);
    }

    private String decodeBase64(String encodePass) {
        byte[] decodeBytes = Base64.getDecoder().decode(encodePass);
        return new String(decodeBytes, StandardCharsets.UTF_8);
    }
}
