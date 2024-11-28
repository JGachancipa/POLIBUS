package com.politecnico.polibus.backend.auth;

import com.politecnico.polibus.backend.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationServiceImplTest {

    @Value("${auth.username}")
    private String validUsername;

    @Value("${auth.password}")
    private String validPassword;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void testValidCredentials() {
        String encodedPassword = "bG96M0FtN2p1czFDemZ1";
        boolean isValid = authenticationService.isValidCredentials(validUsername, encodedPassword);

        assertTrue(isValid, "The credentials should be valid");
    }

    @Test
    public void testInvalidCredentials() {
        String encodedPassword = "dGhpcyBpcyBhIHRlc3Q=";
        boolean isValid = authenticationService.isValidCredentials(validUsername, encodedPassword);

        assertFalse(isValid, "The credentials should be invalid");
    }
}
