package com.example.shopv2.service.user;

import com.example.shopv2.mapper.UserMapper;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.service.dto.UserEntityDTO;
import com.example.shopv2.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserValidator userValidator, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.info("Searching user: " + userName);
        UserEntity user = userRepository.findByUsername(userName);
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public UserEntity saveUser(UserEntityDTO userEntityDTO){
        UserEntity userEntity = userMapper.dtoToEntity(userEntityDTO);
        userValidator.checkIfUserExist(userEntity);
        UserEntity user = userRepository.save(userEntity);
        LOGGER.info("User saved: " + userEntity.getUsername());
        return user;
    }
}
