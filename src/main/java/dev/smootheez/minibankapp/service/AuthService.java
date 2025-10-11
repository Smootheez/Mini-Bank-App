package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
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
        user.setPin(passwordEncoder.encode(request.getPin()));
        String firstName = request.getFirstName();
        user.setFirstName(firstName);
        String lastName = request.getLastName();
        user.setLastName(lastName);
        user.setName(firstName + " " + lastName);
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