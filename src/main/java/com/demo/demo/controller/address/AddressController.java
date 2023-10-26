package com.demo.demo.controller.address;


import com.demo.demo.dto.address.AddressCreateDTO;
import com.demo.demo.dto.address.AddressGetDTO;
import com.demo.demo.dto.address.AddressUpdateDTO;
import com.demo.demo.response.Data;
import com.demo.demo.service.address.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Data<AddressGetDTO>> create(@RequestBody AddressCreateDTO dto) {
        Data<AddressGetDTO> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Data<AddressGetDTO>> update(@PathVariable("id") AddressUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Data<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<AddressGetDTO>> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Data<List<AddressGetDTO>>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }
}