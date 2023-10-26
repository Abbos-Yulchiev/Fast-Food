package com.demo.demo.dto.food;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodGetDTO extends GenericDTO {

    private String name;
    private double calories;
    private String description;
    private String ingredients;
    private double price;
    private String imageUrl;
    private Long duration;
    private String category;
    private String foodStatus;

}
