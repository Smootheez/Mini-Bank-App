package dev.smootheez.minibankapp.exception;

public class InfsufficientFundsException extends RuntimeException {
    public InfsufficientFundsException(String message) {
        super(message);
    }
}
