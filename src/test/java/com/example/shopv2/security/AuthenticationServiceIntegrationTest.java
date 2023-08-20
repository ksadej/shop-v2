package com.example.shopv2.security;

import com.example.shopv2.exceptions.InvalidUsernameOrPasswordException;
import com.example.shopv2.service.dto.UserEntityDTO;
import com.example.shopv2.service.user.UserDetailsServiceImpl;
import com.example.shopv2.validator.enums.AuthenticationEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AuthenticationServiceIntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        authenticationService = new AuthenticationService(userDetailsServiceImpl, jwtService, authenticationManager);
    }

    @Test
    void createAuthenticationToken_throwExceptionWhenUsernameIsInvalid(){
        //given
        initUserEntityDTOInDB();

        UserEntityDTO userEntityDTO = new UserEntityDTO();
        userEntityDTO.setUsername("test");
        userEntityDTO.setPassword("testPass");

        //when
        InvalidUsernameOrPasswordException result =
                assertThrows(InvalidUsernameOrPasswordException.class, () ->
                        authenticationService.createAuthenticationToken(userEntityDTO));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }

    @Test
    void createAuthenticationToken_throwExceptionWhenPasswordIsInvalid(){
        //given
        initUserEntityDTOInDB();

        UserEntityDTO userEntityDTO = new UserEntityDTO();
        userEntityDTO.setUsername("testUser");
        userEntityDTO.setPassword("test");

        //when
        InvalidUsernameOrPasswordException result =
                assertThrows(InvalidUsernameOrPasswordException.class, () ->
                        authenticationService.createAuthenticationToken(userEntityDTO));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }

    private void initUserEntityDTOInDB(){
        UserEntityDTO dto = new UserEntityDTO();
        dto.setUsername("testUser");
        dto.setPassword("testPass");
        userDetailsServiceImpl.saveUser(dto);
    }
}
