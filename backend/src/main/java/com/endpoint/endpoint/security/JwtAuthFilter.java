package com.endpoint.endpoint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.endpoint.endpoint.services.CustomUserDetailsService;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthFilter intercepts every HTTP request to:
 * - Extract the JWT token from the Authorization header
 * - Validate the token using JwtUtil
 * - Load user details with CustomUserDetailsService
 * - Set the authentication in the Spring Security context if the token is valid
 *
 * This ensures that authenticated users can access protected endpoints
 * without needing to log in again on every request.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String path = request.getServletPath();

    // Skip JWT validation for auth endpoints
    if (path.startsWith("/api/auth")) {
        filterChain.doFilter(request, response);
        return;
    }

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);

        // Validate the token
        if (jwtUtil.validateJwtToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            String role = jwtUtil.extractRole(token); // Extract "ROLE_ADMIN" or "ROLE_USER"

            if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Set role as authority
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                List<GrantedAuthority> authorities = List.of(authority);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }

    filterChain.doFilter(request, response);
}


}
