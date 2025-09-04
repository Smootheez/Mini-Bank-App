package dev.smootheez.minibankapp.user.service;

import dev.smootheez.minibankapp.jwt.service.*;
import dev.smootheez.minibankapp.user.*;
import dev.smootheez.minibankapp.user.entity.*;
import dev.smootheez.minibankapp.user.exception.*;
import dev.smootheez.minibankapp.user.repository.*;
import dev.smootheez.minibankapp.user.request.*;
import dev.smootheez.minibankapp.user.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.math.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(UserRegisterRequest userRegisterRequest) {
        String email = userRegisterRequest.getEmail();
        if (userRepository.existsByEmail(email))
            throw new UserAlreadyExistException("User with email " + email + " already exists");

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setBalance(new BigDecimal(0));
        user.setCurrency(userRegisterRequest.getCurrency());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setRole(UserRole.USER);

        userRepository.save(user);

        AuthResponse authResponse = AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        String token = jwtService.generateToken(authResponse);
        authResponse.setToken(token);

        log.info("User {} registered successfully", user.getId());
        return authResponse;
    }

    public AuthResponse login(UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getEmail(),
                            userLoginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        UserEntity user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        AuthResponse authResponse = AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        String token = jwtService.generateToken(authResponse);
        authResponse.setToken(token);

        log.info("User {} logged in successfully", user.getId());
        return authResponse;
    }
}
