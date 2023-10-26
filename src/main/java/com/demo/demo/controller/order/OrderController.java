package com.demo.demo.controller.order;

import com.demo.demo.dto.order.OrderCreateDTO;
import com.demo.demo.dto.order.OrderGetDTO;
import com.demo.demo.dto.order.OrderUpdateDTO;
import com.demo.demo.response.Data;
import com.demo.demo.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Data<OrderGetDTO>> create(@RequestBody OrderCreateDTO dto) {
        Data<OrderGetDTO> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Data<OrderGetDTO>> update(@PathVariable("id") OrderUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Data<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<OrderGetDTO>> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Data<List<OrderGetDTO>>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }
}
