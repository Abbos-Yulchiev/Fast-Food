package com.demo.demo.dto.order;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderUpdateDTO extends GenericDTO {

    private String orderStatus;
    private Long user;
    private Long officiant;
    private int totalPrice;
    private List<Long> foods;
}
