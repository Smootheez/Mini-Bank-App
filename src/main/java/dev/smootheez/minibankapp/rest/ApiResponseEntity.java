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
    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, String[] messages) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), null, messages, Instant.now(), null),
                status
        );
    }

    // ✅ Optional: multiple messages with data
    public static <T> ApiResponseEntity<T> build(HttpStatusCode status, String[] messages, T body) {
        return new ApiResponseEntity<>(
                ApiResponse.build(status.value(), null, messages, Instant.now(), body),
                status
        );
    }
}

// ✅ Fixed record with multiple messages instead of a single String
@JsonInclude(JsonInclude.Include.NON_NULL) // <-- only include non-null fields in JSON
record ApiResponse<T>(
        Integer status,
        String message,        // shown when single message
        String[] messages, // shown when multiple
        Instant timestamp,
        T data
) {
    public static <T> ApiResponse<T> build(int status, String message, String[] messages, Instant timestamp, T data) {
        return new ApiResponse<>(status, message, messages, timestamp, data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(status, that.status) && Objects.equals(message, that.message) && Objects.deepEquals(messages, that.messages) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, Arrays.hashCode(messages), timestamp, data);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", messages=" + Arrays.toString(messages) +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
