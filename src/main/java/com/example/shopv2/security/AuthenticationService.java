package com.example.shopv2.security;

import com.example.shopv2.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserDetailsService userDetailsService,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String createAuthenticationToken(UserEntity userEntity) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userEntity.getUsername(), userEntity.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        return jwtToken;
        }
    }
