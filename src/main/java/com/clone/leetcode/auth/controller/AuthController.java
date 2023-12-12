package com.clone.leetcode.auth.controller;

import com.clone.leetcode.auth.dto.AuthResponse;
import com.clone.leetcode.auth.dto.LoginRequest;
import com.clone.leetcode.auth.dto.RegisterRequest;
import com.clone.leetcode.auth.service.AuthService;
import com.clone.leetcode.system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.baseUrl}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Response<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return new Response<>(true, HttpStatus.OK.value(),
                "Registration Success", authResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Response<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return new Response<>(true, HttpStatus.OK.value(),
                "Login Success", authResponse);
    }
}
