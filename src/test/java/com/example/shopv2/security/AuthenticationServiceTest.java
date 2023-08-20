package com.example.shopv2.security;

import com.example.shopv2.exceptions.InvalidUsernameOrPasswordException;
import com.example.shopv2.service.dto.UserEntityDTO;
import com.example.shopv2.service.user.UserDetailsServiceImpl;
import com.example.shopv2.validator.enums.AuthenticationEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        JWTService jwtService = new JWTService();
        authenticationService =
                new AuthenticationService(userDetailsServiceImpl, jwtService, authenticationManager);
    }

    @Test
    void createAuthenticationToken_returnTokenWhenUserAndPasswordAreOk(){
        //given
        UserEntityDTO userEntityDTO = new UserEntityDTO();
        String userName = "userName";
        String userPassword = "userPassword";
        userEntityDTO.setUsername(userName);
        userEntityDTO.setPassword(userPassword);
        Collection authorities = Collections.emptyList();
        UserDetails userDetails = new User(userName, userPassword , authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userName, userPassword);
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(userDetailsServiceImpl.loadUserByUsername(userName)).thenReturn(userDetails);

        //when
        String result = authenticationService.createAuthenticationToken(userEntityDTO);

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    void createAuthenticationToken_thrownInvalidUserNameOrPasswordException(){
        //given
        UserEntityDTO userEntityDTO = new UserEntityDTO();
        String userName = "userName";
        String userPassword = "userPassword";
        userEntityDTO.setUsername(userName);
        userEntityDTO.setPassword(userPassword);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userName, userPassword);
        when(authenticationManager.authenticate(authToken)).thenThrow(BadCredentialsException.class);

        //when
        InvalidUsernameOrPasswordException result =
                assertThrows(InvalidUsernameOrPasswordException.class, () -> authenticationService.createAuthenticationToken(userEntityDTO));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }



}