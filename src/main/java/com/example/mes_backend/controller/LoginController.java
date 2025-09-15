package com.example.mes_backend.controller;

import com.example.mes_backend.service.LoginService;
import com.example.mes_backend.dto.login.LoginRequest;
import com.example.mes_backend.dto.login.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // SecurityConfig의 permitAll 경로와 일치
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }
}