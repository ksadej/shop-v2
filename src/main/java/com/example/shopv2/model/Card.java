package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "Card")
public class Card {

    @Id
    @Column(name = "CARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    private Long idUser;
}
