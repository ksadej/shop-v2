package com.example.shopv2.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Days {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String formatter;

    Days(String formatter) {
        this.formatter = formatter;
    }

    //formater dla wartości w przypadku których w wartości HTTP podawany były enum z małycj liter
    private static Map<String, Days> FORMAT_MAP = Stream
            .of(Days.values())
            .collect(Collectors.toMap(s -> s.formatter, Function.identity()));

    @JsonCreator
    public static Days fromString(String string) {
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
