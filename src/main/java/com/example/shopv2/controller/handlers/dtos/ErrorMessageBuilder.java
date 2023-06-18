package com.example.shopv2.controller.handlers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageBuilder {

    private String errorCode;
    private String errorDescription;


}
