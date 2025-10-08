package dev.smootheez.minibankapp.exception;

import dev.smootheez.minibankapp.rest.ApiResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponseEntity<Object> handleException(Exception e) {
        return ApiResponseEntity.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + e.getMessage()
        );
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ApiResponseEntity<Object> handleDuplicateEntityException(DuplicateEntityException e) {
        return ApiResponseEntity.build(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
    }

    @ExceptionHandler(BadCredentialException.class)
    public ApiResponseEntity<Object> handleBadCredentialException(BadCredentialException e) {
        return ApiResponseEntity.build(
                HttpStatus.UNAUTHORIZED,
                e.getMessage()
        );
    }
}
