package dev.smootheez.minibankapp.exception;

import dev.smootheez.minibankapp.dto.response.*;
import jakarta.persistence.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponseEntity<String> handleException(Exception e) {
        return ApiResponseEntity.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        );
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ApiResponseEntity<String> handleDuplicateEntityException(DuplicateEntityException e) {
        return ApiResponseEntity.build(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
    }

    @ExceptionHandler(BadCredentialException.class)
    public ApiResponseEntity<String> handleBadCredentialException(BadCredentialException e) {
        return ApiResponseEntity.build(
                HttpStatus.UNAUTHORIZED,
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ApiResponseEntity.build(HttpStatus.BAD_REQUEST, errors.values().toArray(String[]::new));
    }

    @ExceptionHandler(CurrencyMismatchException.class)
    public ApiResponseEntity<String> handleCurrencyMismatchException(CurrencyMismatchException e) {
        return ApiResponseEntity.build(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ApiResponseEntity<String> handleInfsufficientFundsException(InsufficientFundsException e) {
        return ApiResponseEntity.build(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ApiResponseEntity.build(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponseEntity.build(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ApiResponseEntity<Object> handleInvalidTransactionException(InvalidTransactionException e) {
        return ApiResponseEntity.build(
                HttpStatus.NOT_ACCEPTABLE,
                e.getMessage()
        );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ApiResponseEntity<String> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return ApiResponseEntity.build(
                HttpStatus.NOT_IMPLEMENTED,
                e.getMessage()
        );
    }
}
