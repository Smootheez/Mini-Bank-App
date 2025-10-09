package dev.smootheez.minibankapp.common.exception;

public class InfsufficientFundsException extends RuntimeException {
    public InfsufficientFundsException(String message) {
        super(message);
    }
}
