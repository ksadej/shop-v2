package com.example.shopv2.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MealTime{
    MORNING,
    NOON,
    EVENING;
}
