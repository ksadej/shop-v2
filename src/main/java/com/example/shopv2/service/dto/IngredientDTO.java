package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public Object nameClean;
    public String original;
    public Object originalName;
    public double amount;
    public String unit;
}
