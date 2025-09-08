package dev.smootheez.minibankapp.user.controller;

import dev.smootheez.minibankapp.user.http.request.*;
import dev.smootheez.minibankapp.user.http.response.*;
import dev.smootheez.minibankapp.user.service.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("Receiving register request");
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest request) {
        log.info("Receiving login request");
        return ResponseEntity.ok(authService.login(request));
    }
}
