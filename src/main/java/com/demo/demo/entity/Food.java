package com.demo.demo.entity;

import com.demo.demo.entity.audit.Auditable;
import com.demo.demo.entity.enums.FoodCategory;
import com.demo.demo.entity.enums.FoodStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Foods")
public class Food extends Auditable {


    @Column(nullable = false, length = 50)
    private String name;

    private double calories;

    @Column(nullable = false)
    private String description;

    private String ingredients;

    private double price;

    private String imageUrl;

    // preparation time (used long to store milliseconds)
    private Long preparationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodStatus foodStatus;
}
