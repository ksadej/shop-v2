package com.example.shopv2.security;

import com.example.shopv2.exceptions.InvalidUsernameOrPasswordException;
import com.example.shopv2.mapper.UserMapper;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.service.dto.UserEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

    public String createAuthenticationToken(UserEntityDTO userEntityDTO) {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userEntityDTO.getUsername(), userEntityDTO.getPassword()));

        }catch (BadCredentialsException | InternalAuthenticationServiceException e){
            throw new InvalidUsernameOrPasswordException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntityDTO.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        return jwtToken;
        }
    }
