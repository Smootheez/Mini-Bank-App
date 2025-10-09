package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
import jakarta.persistence.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Transactional
public abstract class AbstractTransactionService<T, R> {
    protected final UserRepository userRepository;
    protected final PasswordEncoder passwordEncoder;

    protected AbstractTransactionService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    protected static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";

    public abstract R doTransaction(String email, T request);

    protected UserEntity getUserEntity(String email, String userNotFoundWithEmail) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(userNotFoundWithEmail + email)
        );
    }

    protected void validatePin(String rawPin, UserEntity byUser) {
        if (!passwordEncoder.matches(rawPin, byUser.getPin()))
            throw new BadCredentialException("Invalid PIN");
    }
}

