package com.example.shopv2.validator.enums;

public enum FilterParametersEnum {
    FROM_DATE("from"),
    TO_DATE("to"),
    YEAR("year"),
    MONTH("month"),
    CATEGORY("category");

    private final String key;

    FilterParametersEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
