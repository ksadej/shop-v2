package com.example.shopv2.controller;

import com.example.shopv2.model.UserEntity;
import com.example.shopv2.security.AuthenticationService;
import com.example.shopv2.service.UserDetailsServiceImpl;
import com.example.shopv2.service.dto.UserEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String getAuthenticationToken(@RequestBody UserEntity userEntity) {
        return authenticationService.createAuthenticationToken(userEntity);
    }

    @PostMapping
    public UserEntity setUserDetails(@RequestBody UserEntityDTO userEntity) {
        return userDetailsService.saveUser(userEntity);
    }

}
