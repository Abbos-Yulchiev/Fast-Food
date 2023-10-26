package com.demo.demo.dto.order;

import com.demo.demo.dto.GenericDTO;
import com.demo.demo.entity.Food;
import com.demo.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderGetDTO extends GenericDTO {

    private String orderStatus;
    private User user;
    private User officiant;
    private int totalPrice;
    private List<Food> foods;
}
