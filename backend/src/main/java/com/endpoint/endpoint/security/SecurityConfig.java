/**
 * SecurityConfig sets up basic security for the Book API.
 *
 * - Defines two in-memory users: "user" with role USER, and "admin" with role ADMIN.
 * - Secures endpoints under "/books/**" to require authentication.
 * - Enables method-level security with @PreAuthorize annotations.
 * - Uses HTTP Basic authentication for simplicity.
 * - Disables CSRF for testing purposes (not recommended for production).
 */

package com.endpoint.endpoint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.endpoint.endpoint.services.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize for method level security
public class SecurityConfig {
        @Autowired
        private JwtAuthFilter jwtAuthFilter;
        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/auth/**", "/api/**"))
                                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/h2-console/**", "/api/auth/**").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /**
         * Exposes the AuthenticationManager bean which is required for manual
         * authentication
         * (e.g., during login with username and password). It delegates authentication
         * logic
         * to the configured UserDetailsService and PasswordEncoder.
         */

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

}
