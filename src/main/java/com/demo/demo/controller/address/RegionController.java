package com.demo.demo.controller.address;

import com.demo.demo.dto.regions.RegionCreateDTO;
import com.demo.demo.dto.regions.RegionGetDTO;
import com.demo.demo.dto.regions.RegionUpdateDTO;
import com.demo.demo.response.Data;
import com.demo.demo.service.address.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/region")
@CrossOrigin
public class RegionController {

    private final RegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Data<RegionGetDTO>> create(@RequestBody RegionCreateDTO dto) {
        Data<RegionGetDTO> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Data<RegionGetDTO>> update(@PathVariable("id") RegionUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Data<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<RegionGetDTO>> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Data<List<RegionGetDTO>>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                           @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }
}