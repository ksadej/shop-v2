package com.example.shopv2.mapper;

import com.example.shopv2.service.dto.UserEntityDTO;
import com.example.shopv2.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{

    public UserEntity dtoToEntity(UserEntityDTO userEntityDTO) {
        return UserEntity
                .builder()
                .username(userEntityDTO.getUsername())
                .password(userEntityDTO.getPassword())
                .build();
    }

    public UserEntityDTO entityToDto(UserEntity userEntity){
        return UserEntityDTO
                .builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }
}
