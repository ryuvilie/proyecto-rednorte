package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.dto.AuthResponse;
import com.rednorte.gestionclinica.dto.LoginRequest;
import com.rednorte.gestionclinica.dto.RegisterRequest;
import com.rednorte.gestionclinica.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://32.197.111.18:5173"
})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}