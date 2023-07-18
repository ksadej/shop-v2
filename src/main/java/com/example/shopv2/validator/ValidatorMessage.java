package com.example.shopv2.validator;

public class ValidatorMessage {

    private StringBuilder message = new StringBuilder();
    private StringBuilder code = new StringBuilder();

    public String getMessage() {
        return message.toString();
    }

    public void setMessage(String message) {
        this.message.append(message);
    }

    public String getCode() {
        return code.toString();
    }

    public void setCode(String code) {
        this.code.append(code);
    }
}
