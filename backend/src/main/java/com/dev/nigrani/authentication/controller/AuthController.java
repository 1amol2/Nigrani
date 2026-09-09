package com.dev.nigrani.authentication.controller;

import com.dev.nigrani.authentication.dtos.*;
import com.dev.nigrani.authentication.models.User;
import com.dev.nigrani.authentication.service.AuthService;
import com.dev.nigrani.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}