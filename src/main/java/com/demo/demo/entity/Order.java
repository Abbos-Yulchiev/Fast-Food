package com.demo.demo.entity;

import com.demo.demo.entity.audit.Auditable;
import com.demo.demo.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orders")
public class Order extends Auditable {

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private double destination;

    private long deliveryTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private User officiant;

    private int totalPrice;

    @OneToMany
    private List<Food> foods;
}
