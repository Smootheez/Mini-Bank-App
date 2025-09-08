package dev.smootheez.minibankapp.user.service;

import dev.smootheez.minibankapp.core.exception.*;
import dev.smootheez.minibankapp.security.jwt.*;
import dev.smootheez.minibankapp.user.http.request.*;
import dev.smootheez.minibankapp.user.http.response.*;
import dev.smootheez.minibankapp.user.model.*;
import dev.smootheez.minibankapp.user.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistException("User with that email already exists");

        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPin(passwordEncoder.encode(request.getPin()));
        user.setCurrency(request.getCurrency());

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        log.info("Successfully registering user...");
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid credentials");

        String token = jwtService.generateToken(user);

        log.info("Successfully logging in user...");
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
