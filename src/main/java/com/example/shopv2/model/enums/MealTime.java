package com.example.shopv2.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MealTime{
    MORNING("morning"),
    NOON("noon"),
    EVENING("formatter");

    private final String formatter;

    MealTime(String formatter) {
        this.formatter = formatter;
    }

    //formater dla wartości w przypadku których w wartości HTTP podawany były enum z małycj liter
    private static Map<String, MealTime> FORMAT_MAP = Stream
            .of(MealTime.values())
            .collect(Collectors.toMap(s -> s.formatter, Function.identity()));

    @JsonCreator
    public static MealTime fromString(String string) {
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
