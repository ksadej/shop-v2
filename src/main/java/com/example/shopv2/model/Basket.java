package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<Nutrition> nutritionList = new ArrayList<>();

    private Long idUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;
    private OffsetDateTime dataAdded;

}
