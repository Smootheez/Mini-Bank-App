package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.domain.model.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
import dev.smootheez.minibankapp.rest.dto.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        log.debug("Registering user: {}", request);

        String requestEmail = request.getEmail();
        if (userRepository.existsByEmail(requestEmail))
            throw new DuplicateEntityException("User with email " + requestEmail + " already exists");

        var user = new UserEntity();
        user.setEmail(requestEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPin(request.getPin());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCurrency(request.getCurrency());

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail());
    }

    public String login(LoginRequest request) {
        log.debug("Logging in user: {}", request);
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadCredentialException("Invalid email or password");

        return jwtService.generateToken(user.getEmail());
    }
}