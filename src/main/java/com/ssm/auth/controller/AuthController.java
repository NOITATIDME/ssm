package com.ssm.auth.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssm.auth.dto.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

    @GetMapping("/auth/success")
    public ResponseEntity<String> loginSuccess(@RequestParam String code) {
        return ResponseEntity.ok(code);
    }

    @GetMapping("/user")
    public Principal loginUser(Principal user) {
        return user;
    }
}
