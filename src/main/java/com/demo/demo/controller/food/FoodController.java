package com.demo.demo.controller.food;


import com.demo.demo.dto.food.FoodCreateDTO;
import com.demo.demo.dto.food.FoodGetDTO;
import com.demo.demo.dto.food.FoodUpdateDTO;
import com.demo.demo.response.Data;
import com.demo.demo.service.food.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/food")
@CrossOrigin
public class FoodController  {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Data<FoodGetDTO>> create(@RequestBody FoodCreateDTO dto) {
        Data<FoodGetDTO> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Data<FoodGetDTO>> update(@PathVariable("id") FoodUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Data<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<FoodGetDTO>> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Data<List<FoodGetDTO>>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                         @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }
}
