package com.demo.demo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderCreateDTO {

    private String orderStatus;
    private Long user;
    private Long officiant;
    private int totalPrice;
    private List<Long> foods;
    private double distance;
}
