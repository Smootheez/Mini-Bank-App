package dev.smootheez.minibankapp.user.controller;

import dev.smootheez.minibankapp.user.request.*;
import dev.smootheez.minibankapp.user.response.*;
import dev.smootheez.minibankapp.user.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authService.register(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.login(userLoginRequest));
    }
}
