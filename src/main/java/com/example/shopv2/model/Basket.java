package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "BASKET")
public class Basket {

    @Id
    @Column(name = "BASKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<Recipes> recipes = new ArrayList<>();
    private Long idUser;
}
