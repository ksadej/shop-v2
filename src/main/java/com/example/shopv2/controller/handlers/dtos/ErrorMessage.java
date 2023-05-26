package com.example.shopv2.controller.handlers.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String errorCode;
    private String errorDescription;
}
