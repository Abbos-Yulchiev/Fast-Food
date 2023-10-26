package com.demo.demo.controller.auth;

import com.demo.demo.core.security.CurrentUser;
import com.demo.demo.dto.security.LoginDTO;
import com.demo.demo.entity.User;
import com.demo.demo.response.Data;
import com.demo.demo.service.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/authorization")
@CrossOrigin
public class AuthController {

    final private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Data<String>> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(service.login(loginDTO));
    }

    @GetMapping("/authToken")
    public ResponseEntity<?> authToken(@CurrentUser User user) {
        return ResponseEntity.ok(service.authToken(user));
    }
}
