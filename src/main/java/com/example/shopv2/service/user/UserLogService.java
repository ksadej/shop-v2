package com.example.shopv2.service.user;

import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserLogService {

    final private UserRepository userRepository;

    public UserLogService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity loggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User username = (User) authentication.getPrincipal();

        UserEntity result = userRepository.findByUsername(username.getUsername());
        return result;
    }
}
