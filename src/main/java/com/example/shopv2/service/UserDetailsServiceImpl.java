package com.example.shopv2.service;

import com.example.shopv2.exceptions.UserException;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.validator.UserValidator;
import com.example.shopv2.validator.enums.AuthenticationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.info("Searching user: " + userName);
        UserEntity user = userRepository.findByUsername(userName);
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public UserEntity saveUser(UserEntity userEntity){
        userValidator.checkIfUserExist(userEntity);
        UserEntity user = userRepository.save(userEntity);
        LOGGER.info("User saved: " + userEntity.getUsername());
        return user;
    }
}
