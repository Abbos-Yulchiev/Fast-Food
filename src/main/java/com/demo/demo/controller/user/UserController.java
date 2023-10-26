package com.demo.demo.controller.user;

import com.demo.demo.dto.user.UserCreateDTO;
import com.demo.demo.dto.user.UserGetDTO;
import com.demo.demo.dto.user.UserUpdateDTO;
import com.demo.demo.response.Data;
import com.demo.demo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController  {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Data<UserGetDTO>> create(@RequestBody UserCreateDTO dto) {
        Data<UserGetDTO> response = service.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Data<UserGetDTO>> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Data<String>> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Data<UserGetDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }


    @GetMapping
    public ResponseEntity<Data<List<UserGetDTO>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Data<List<UserGetDTO>>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                         @RequestParam(defaultValue = "20", required = false) Integer size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }


    @PostMapping("/deactivateUser/{id}")
    public ResponseEntity<Data<UserGetDTO>> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.activateUser(id));
    }
}