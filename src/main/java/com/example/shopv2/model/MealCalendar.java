package com.example.shopv2.model;

import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "MEAL_CALENDAR")
public class MealCalendar {

    @Id
    @Column(name = "MEAL_CALENDAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer idRecipes;
    @Enumerated(EnumType.STRING)
    private Days day;
    @Enumerated(EnumType.STRING)
    private MealTime time;
    private OffsetDateTime dataMeal;

    public MealCalendar(Integer idRecipes, Days day, MealTime time, OffsetDateTime dataMeal) {
        this.idRecipes = idRecipes;
        this.day = day;
        this.time = time;
        this.dataMeal = dataMeal;
    }
}
