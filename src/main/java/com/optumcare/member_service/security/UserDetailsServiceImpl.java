package com.optumcare.member_service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement logic to retrieve user from database
        // For example, using a UserRepository to fetch user details
        // return new User(user.getUsername(), user.getPassword(), authorities);
        throw new UsernameNotFoundException("User not found");
    }
}
