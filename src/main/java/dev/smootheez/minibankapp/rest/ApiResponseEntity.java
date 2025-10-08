package dev.smootheez.minibankapp.rest;

import org.springframework.http.*;

import java.time.*;

public class ApiResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {
    private ApiResponseEntity(ApiResponse<T> body, HttpStatusCode status) {
        super(body, status);
    }

    public static <T> ApiResponseEntity<T> build(HttpStatus status, String message, T body) {
        return new ApiResponseEntity<>(ApiResponse.build(status.value(), message, Instant.now(), body), status);
    }

    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, String message) {
        return new ApiResponseEntity<>(ApiResponse.build(status.value(), message, Instant.now(), null), status);
    }
}

record ApiResponse<T>(Integer status, String message, Instant timestamp, T data) {
    static <T> ApiResponse<T> build(int status, String message, Instant timestamp, T data) {
        return new ApiResponse<>(status, message, timestamp, data);
    }
}
