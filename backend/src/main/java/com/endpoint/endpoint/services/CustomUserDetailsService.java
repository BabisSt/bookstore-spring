package com.endpoint.endpoint.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.repositories.UserRepository;

import java.util.Collections;

/*File to handle the JWT process of verifying the users
 * implements UserDetailsService : Core interface which loads user-specific data.
 * It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider. 
 * The interface requires only one read-only method, which simplifies support for new data-access strategies.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        // Wrap the user's role as a Spring Security authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority));
    }
}
