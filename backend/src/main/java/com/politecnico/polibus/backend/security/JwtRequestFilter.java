package com.politecnico.polibus.backend.security;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("Processing request: {}", request.getRequestURI());
        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Processing auth: {}", authorizationHeader);
        String username = null;
        String jwt = null;



        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            logger.debug("Extracted username from JWT: {}", username);
        } else {
            logger.warn("Authorization header is missing or invalid");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt)) {
                logger.info("Token is valid for user: {}", username);
                String predefinedRole = "USER";
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singleton(() -> predefinedRole));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User authenticated: {}", username);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        } else {
            logger.warn("Username is null or already authenticated");
        }

        chain.doFilter(request, response);
    }

}
