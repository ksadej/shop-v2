package com.example.shopv2.validator;

import com.example.shopv2.exceptions.UserException;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.validator.enums.AuthenticationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UserValidator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkIfUserExist(UserEntity userEntity){
        boolean exists = userRepository.existsByUsername(userEntity.getUsername());

        if(exists){
            throw new UserException(AuthenticationEnum.USER_EXISTS.getMessage());
        }
    }
}
