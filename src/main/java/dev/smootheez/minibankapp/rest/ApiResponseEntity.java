package dev.smootheez.minibankapp.rest;

import com.fasterxml.jackson.annotation.*;
import org.springframework.http.*;

import java.time.*;
import java.util.*;

public class ApiResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {

    private ApiResponseEntity(ApiResponse<T> body, HttpStatusCode status) {
        super(body, status);
    }

    // ✅ 1. Single message with body
    public static <T> ApiResponseEntity<T> build(HttpStatus status, String message, T body) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), message, null, Instant.now(), body),
                status
        );
    }

    // ✅ 2. Single message without body
    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, String message) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), message, null, Instant.now(), null),
                status
        );
    }

    // ✅ 3. Multiple messages
    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, List<String> messages) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), null, messages, Instant.now(), null),
                status
        );
    }

    // ✅ Optional: multiple messages with data
    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, List<String> messages, T body) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), null, messages, Instant.now(), body),
                status
        );
    }
}

// ✅ Fixed record with List<String> messages instead of a single String
@JsonInclude(JsonInclude.Include.NON_NULL) // <-- only include non-null fields in JSON
record ApiResponse<T>(
        Integer status,
        String message,        // shown when single message
        List<String> messages, // shown when multiple
        Instant timestamp,
        T data
) {
    public static <T> ApiResponse<T> build(int status, String message, List<String> messages, Instant timestamp, T data) {
        return new ApiResponse<>(status, message, messages, timestamp, data);
    }
}
