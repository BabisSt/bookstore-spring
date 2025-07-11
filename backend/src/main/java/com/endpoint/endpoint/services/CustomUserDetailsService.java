package com.endpoint.endpoint.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.repositories.UserRepository;

/*File to handle the JWT process of verifying the users
 * implements UserDetailsService : Core interface which loads user-specific data.
 * It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider. 
 * The interface requires only one read-only method, which simplifies support for new data-access strategies.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /* Load user by email (used as username) */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("This user doen't exists");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true,
                true, true, AuthorityUtils.NO_AUTHORITIES);
    }
}
