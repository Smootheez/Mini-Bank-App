package dev.smootheez.minibankapp.controller;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.service.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Value("${jwt.expiration}")
    private long maxAge;

    private static final String COOKIE_NAME = "jwt"; // Name of the cookie
    private static final boolean HTTP_ONLY = true; // HttpOnly prevents client-side scripts from accessing the cookie
    private static final String SAME_SITE = "Lax"; // "Strict" for same origins or "Lax" if frontend & backend are different origins
    private static final boolean SECURE = false; // False for development "http", change it to true for production "https"

    @PostMapping("/register")
    public ApiResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse servletResponse) {
        // Set JWT into HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, authService.register(request))
                .httpOnly(HTTP_ONLY)
                .secure(SECURE) // set to false for local dev if not using HTTPS
                .sameSite(SAME_SITE)
                .path("/")
                .maxAge(maxAge / 1000)
                .build();

        servletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponseEntity.build(HttpStatus.CREATED, "Registration successfully!");
    }

    @PostMapping("/login")
    public ApiResponseEntity<Object> login(@Valid @RequestBody LoginRequest request, HttpServletResponse servletResponse) {
        // Set JWT into HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, authService.login(request))
                .httpOnly(HTTP_ONLY)
                .secure(SECURE) // set to false for local dev if not using HTTPS
                .sameSite(SAME_SITE)
                .path("/")
                .maxAge(maxAge / 1000)
                .build();

        servletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponseEntity.build(HttpStatus.OK, "Login successfully!");
    }

    @PostMapping("/logout")
    public ApiResponseEntity<Object> logout(HttpServletResponse servletResponse) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(HTTP_ONLY)
                .secure(SECURE)
                .sameSite(SAME_SITE)
                .path("/")
                .maxAge(0) // Set maxAge to 0 to delete the cookie
                .build();

        servletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponseEntity.build(HttpStatus.OK, "Logout successfully!");
    }
}
