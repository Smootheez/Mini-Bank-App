package dev.smootheez.minibankapp.user.exception;

public class WrongCredentialException extends RuntimeException {
    public WrongCredentialException(String message) {
        super(message);
    }
}
