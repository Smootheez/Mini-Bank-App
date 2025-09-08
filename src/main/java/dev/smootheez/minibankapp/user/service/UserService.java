package dev.smootheez.minibankapp.user.service;

import dev.smootheez.minibankapp.core.exception.*;
import dev.smootheez.minibankapp.user.http.request.*;
import dev.smootheez.minibankapp.user.http.response.*;
import dev.smootheez.minibankapp.user.model.*;
import dev.smootheez.minibankapp.user.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * === User delete ===
     * @param email User's email
     */
    @Transactional
    public void delete(String email, UserDeleteRequest request) {
        UserEntity user = loadUserByEmail(email);

        // Password check (authentication)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid credentials");

        // Email check (authorization)
        if (!user.getEmail().equals(request.getEmail()))
            throw new IllegalArgumentException("Email does not match");

        userRepository.delete(user); // Delete user from the database
        log.info("Successfully deleted user");
    }

    private UserEntity loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * === User info ===
     * @param email User's email
     */
    @Transactional(readOnly = true)
    public UserInfoResponse info(String email) {
        log.info("Successfully received user info request");
        return userRepository.getUserInfo(email); // Get user info from the database
    }

    /**
     * Update a user's info'
     * @param email User's email
     * @param request User's update request {@link UserUpdateRequest}
     */
    @Transactional
    public UserInfoResponse update(String email, UserUpdateRequest request) {
        UserEntity user = loadUserByEmail(email);

        // Password check (authentication)
        validatePassword(request, user);

        // === Only update values if they are changed ===
        updateFirstName(request, user); // Update a user's first name'
        updateLastName(request, user); // Update a user's last name'
        updateEmail(request, user); // Update a user's email'
        updatePin(request, user); // Update a user's PIN'
        updatePassword(request, user); // Update a user's password'

        userRepository.save(user); // Save changes to the database
        log.info("Successfully updated user info request");
        return userRepository.getUserInfo(user.getEmail());
    }

    private void updatePassword(UserUpdateRequest request, UserEntity user) {
        String newPassword = request.getPassword();
        if (newPassword != null && !newPassword.isBlank()
                && !passwordEncoder.matches(newPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Change a user's password
        }
    }

    private void updatePin(UserUpdateRequest request, UserEntity user) {
        String newPin = request.getNewPin();
        if (newPin != null && !newPin.isBlank()
                && !passwordEncoder.matches(newPin, user.getPin())) {
            user.setPin(passwordEncoder.encode(newPin)); // Change user's PIN
        }
    }

    private void updateEmail(UserUpdateRequest request, UserEntity user) {
        if (isValidAndChanged(request.getNewEmail(), user.getEmail())) {
            if (userRepository.existsByEmail(request.getNewEmail())) { // Check if email already exists
                throw new UserAlreadyExistException("User with the same email already exists"); // Email already exists exception
            }
            user.setEmail(request.getNewEmail().trim()); // Change user's email
        }
    }

    private static void updateLastName(UserUpdateRequest request, UserEntity user) {
        if (isValidAndChanged(request.getNewLastName(), user.getLastName())) {
            user.setLastName(request.getNewLastName().trim()); // Change a user's last name
        }
    }

    private static void updateFirstName(UserUpdateRequest request, UserEntity user) {
        if (isValidAndChanged(request.getNewFirstName(), user.getFirstName())) {
            user.setFirstName(request.getNewFirstName().trim()); // Change a user's first name
        }
    }

    private void validatePassword(UserUpdateRequest request, UserEntity user) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    private static boolean isValidAndChanged(String newValue, String currentValue) {
        return newValue != null &&
                !newValue.isBlank() &&
                !newValue.equals(currentValue);
    }
}
