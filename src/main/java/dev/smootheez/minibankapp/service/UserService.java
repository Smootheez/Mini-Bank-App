package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
import jakarta.persistence.*;
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

    public UserInfoResponse getUserInfo(String email) {
        return userRepository.getUserInfoByEmail(email);
    }

    @Transactional
    public void updatePassword(String email, UpdatePasswordRequest request) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid old password");

        String newPassword = request.getNewPassword();
        if (!newPassword.equals(request.getConfirmNewPassword()))
            throw new IllegalArgumentException("New password and confirm new password do not match");

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void updatePin(String email, UpdatePinRequest request) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );

        if (!passwordEncoder.matches(request.getOldPin(), user.getPin()))
            throw new IllegalArgumentException("Invalid old PIN");

        String newPin = request.getNewPin();
        if (!newPin.equals(request.getConfirmNewPin()))
            throw new IllegalArgumentException("New PIN and confirm new PIN do not match");

        user.setPin(passwordEncoder.encode(newPin));
    }
}
