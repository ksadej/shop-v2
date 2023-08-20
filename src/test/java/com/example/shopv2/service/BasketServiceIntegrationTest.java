package com.example.shopv2.service;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.service.user.UserLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@WithMockUser
public class BasketServiceIntegrationTest {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BasketService basketService;
    @Mock
    private UserLogService userLogService;

    @Test
    void deleteBasketByUser_deleteBasketByUser(){
        //given
        List<Basket> basketBeforeDelete = basketRepository.findAll();
        initBasketDB(initUser());

        //when
        basketService.deleteBasketByUser();

        //then
        assertThat(basketBeforeDelete).hasSize(basketBeforeDelete.size());
    }

    UserEntity initUser(){
        UserEntity user = new UserEntity();
        user.setUsername("userTest");
        user.setPassword("passTest");
        return userRepository.save(user);
    }

    void initBasketDB(UserEntity userEntity){
        Basket basket = Basket
                .builder()
                .dataAdded(OffsetDateTime.parse("2023-07-21T14:56:04+02:00"))
                .userEntity(userEntity)
                .build();

        basketRepository.saveAll(List.of(basket));
    }
}
