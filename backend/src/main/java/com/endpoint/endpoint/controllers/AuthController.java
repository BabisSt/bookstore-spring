package com.endpoint.endpoint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.endpoint.endpoint.dto.JwtResponse;
import com.endpoint.endpoint.dto.LoginRequest;
import com.endpoint.endpoint.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AuthController handles authentication-related endpoints, such as user login.
 * 
 * - It exposes a POST endpoint `/api/auth/login` that accepts user credentials.
 * - It uses the AuthenticationManager to authenticate the user.
 * - On successful authentication, it generates a JWT using JwtUtil and returns
 * it.
 * - If credentials are invalid, it returns a 401 Unauthorized response.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            String token = jwtUtil.generateToken(loginRequest.getEmail());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

    }

}
