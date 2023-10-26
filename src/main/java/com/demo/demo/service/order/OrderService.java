package com.demo.demo.service.order;

import com.demo.demo.core.exception.DataNotFoundException;
import com.demo.demo.core.exception.UnknownDataBaseException;
import com.demo.demo.dto.order.OrderCreateDTO;
import com.demo.demo.dto.order.OrderGetDTO;
import com.demo.demo.dto.order.OrderUpdateDTO;
import com.demo.demo.entity.Order;
import com.demo.demo.repository.order.OrderRepository;
import com.demo.demo.response.Data;
import com.demo.demo.service.food.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.demo.demo.service.food.FoodService.DISHES_PER_PREPARATION_UNIT;
import static com.demo.demo.service.food.FoodService.PREPARATION_TIME_UNIT;

@Service
public class OrderService {

    private final FoodService foodService;
    private final OrderRepository repository;
    private final ModelMapper mapper;

    public OrderService(FoodService foodService, OrderRepository repository, ModelMapper mapper) {
        this.foodService = foodService;

        this.repository = repository;
        this.mapper = mapper;
    }

    public Data<OrderGetDTO> create(OrderCreateDTO dto) {
        int totalPreparationTime = foodService.calculateTotalPreparationTime(dto);
        int deliveryTime = calculateDeliveryTime(dto.getDistance());

        int estimatedDeliveryTime = totalPreparationTime + deliveryTime;
        Order order = new Order();
        mapper.map(dto, order);
        order.setDeliveryTime(estimatedDeliveryTime);
        return new Data<>(convertToOrderGetDTO(order));
    }

    @Transactional
    public Data<OrderGetDTO> update(OrderUpdateDTO dto) {

        Order order = repository.findById(dto.getId()).orElseThrow(() ->
                new DataNotFoundException("Order with ID: [" + dto.getId() + "] was not found"));
        mapper.map(dto, order);

        return new Data<>(convertToOrderGetDTO(order));
    }

    public Data<String> delete(Long id) {
        Optional<Order> byId = repository.findById(id);
        byId.ifPresent(repository::delete);
        return new Data<>("Order was successfully deleted");
    }

    public Data<OrderGetDTO> get(Long id) {
        Order order = repository.findById(id).orElseThrow(() ->
                new UnknownDataBaseException("Order with ID: [%s] was not found".formatted(id)));
        return new Data<>(convertToOrderGetDTO(order));
    }

    public Data<List<OrderGetDTO>> getAll() {
        List<Order> orders = repository.findAll();
        List<OrderGetDTO> orderList = new ArrayList<>();
        for (Order order : orders)
            orderList.add(convertToOrderGetDTO(order));

        return new Data<>(orderList, orderList.size());
    }

    public Data<List<OrderGetDTO>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = repository.findAll(pageable);
        List<OrderGetDTO> orderList = new ArrayList<>();
        for (Order order : orders)
            orderList.add(convertToOrderGetDTO(order));
        return new Data<>(orderList, orderList.size());
    }

    private OrderGetDTO convertToOrderGetDTO(Order order) {
        Order saved = repository.save(order);
        OrderGetDTO orderGetDTO = new OrderGetDTO();
        mapper.map(saved, orderGetDTO);
        return orderGetDTO;
    }

    private int calculateDeliveryTime(double distanceInKilometers) {
        return (int) Math.round(distanceInKilometers * FoodService.DELIVERY_TIME_PER_KM);
    }

    private int calculateTotalPreparationTime(Order order) {
        int totalDishes = order.getFoods().size();

        int totalPreparationUnits = (int) Math.ceil((double) totalDishes / DISHES_PER_PREPARATION_UNIT);
        int totalPreparationTime = totalPreparationUnits * PREPARATION_TIME_UNIT;

        return totalPreparationTime;
    }
}
