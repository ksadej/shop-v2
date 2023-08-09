package com.example.shopv2.service;

import com.example.shopv2.exceptions.UserException;
import com.example.shopv2.mapper.UserMapper;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.service.dto.UserEntityDTO;
import com.example.shopv2.service.user.UserDetailsServiceImpl;
import com.example.shopv2.validator.UserValidator;
import com.example.shopv2.validator.enums.AuthenticationEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserDetailsServiceImpl userDetailsService;
    private UserMapper userMapper = new UserMapper();

    @BeforeEach
    public void setup(){
        userRepository = mock(UserRepository.class);
        userValidator = mock(UserValidator.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository, userValidator, userMapper);
    }

    private void initUser(){

    }

    @Test
    void loadUserByUsername_shouldReturnUserWithUsernameAndPassword(){
        //given
        UserEntity user = new UserEntity();
        user.setUsername("test_name");
        user.setPassword("test_password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        //when
        UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());

        //then
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void saveUser_checkSavedUserInToDataBase(){
        //given
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername("test_name");
        expectedUser.setPassword("test_password");

        UserEntityDTO actualdUser = new UserEntityDTO();
        expectedUser.setUsername("test_name");
        expectedUser.setPassword("test_password");

        when(userRepository.existsByUsername(expectedUser.getUsername())).thenReturn(true);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        //when
        UserEntity result = userDetailsService.saveUser(actualdUser);

        //then

        assertAll(
                ()->assertThat(result.getUsername()).isEqualTo(expectedUser.getUsername()),
                ()->assertThat(result.getPassword()).isEqualTo(expectedUser.getPassword())
        );
    }

    @Test
    void saveUser_throwExceptionWhenUserAlreadyExistsInDatabase(){
        //given
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUsername("test_name");
        expectedUser.setPassword("test_password");

        UserEntityDTO actualdUser = new UserEntityDTO();
        expectedUser.setUsername("test_name");
        expectedUser.setPassword("test_password");
        when(userRepository.existsByUsername(expectedUser.getUsername())).thenReturn(false);

        //when
        UserException result = assertThrows(UserException.class, () -> userDetailsService.saveUser(actualdUser));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.USER_EXISTS);
    }
}