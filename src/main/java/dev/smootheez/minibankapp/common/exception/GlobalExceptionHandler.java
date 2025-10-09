package dev.smootheez.minibankapp.common.exception;

import dev.smootheez.minibankapp.common.payload.*;
import jakarta.persistence.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponseEntity<Object> handleException(Exception e) {
        return ApiResponseEntity.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ApiResponseEntity.build(HttpStatus.BAD_REQUEST, errors.values().toArray(String[]::new));
    }

    @ExceptionHandler(CurrencyMismatchException.class)
    public ApiResponseEntity<Object> handleCurrencyMismatchException(CurrencyMismatchException e) {
        return ApiResponseEntity.build(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(InfsufficientFundsException.class)
    public ApiResponseEntity<Object> handleInfsufficientFundsException(InfsufficientFundsException e) {
        return ApiResponseEntity.build(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ApiResponseEntity.build(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
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
}
