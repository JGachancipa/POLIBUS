package com.politecnico.polibus.backend.controller;

import com.politecnico.polibus.backend.model.AuthRequest;
import com.politecnico.polibus.backend.security.JwtUtil;
import com.politecnico.polibus.backend.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String ERROR_MISSING_CREDENTIALS = "Username and password are required";
    private static final String ERROR_UNAUTHORIZED = "Invalid username or password";

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_MISSING_CREDENTIALS);
        }

        if (authenticationService.isValidCredentials(authRequest.getUsername(), authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ERROR_UNAUTHORIZED);
    }
}
