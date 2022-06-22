package com.springboot.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blogapp.dao.UserRepository;
import com.springboot.blogapp.entities.User;
import com.springboot.blogapp.exception.ResourceNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email: " + username, 0));          
        return user;
    }
    
}
