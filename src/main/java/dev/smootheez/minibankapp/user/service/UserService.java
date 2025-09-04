package dev.smootheez.minibankapp.user.service;

import dev.smootheez.minibankapp.user.entity.*;
import dev.smootheez.minibankapp.user.exception.*;
import dev.smootheez.minibankapp.user.repository.*;
import dev.smootheez.minibankapp.user.request.*;
import dev.smootheez.minibankapp.user.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserInfo(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        return mappedToUserResponse(user);
    }

    public UserResponse updateUser(String email, UserUpdateRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (isNonEmpty(request.getFirstName())) {
            if (request.getFirstName().equals(user.getFirstName())) {
                throw new SameValueException("First name is the same as the current value.");
            }
            user.setFirstName(request.getFirstName());
        }

        if (isNonEmpty(request.getLastName())) {
            if (request.getLastName().equals(user.getLastName())) {
                throw new SameValueException("Last name is the same as the current value.");
            }
            user.setLastName(request.getLastName());
        }

        if (isNonEmpty(request.getEmail())) {
            if (request.getEmail().equals(user.getEmail())) {
                throw new SameValueException("Email is the same as the current value.");
            }
            user.setEmail(request.getEmail()); // ⚠️ Only if email is allowed to be updated
        }

        if (isNonEmpty(request.getPassword())) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new SameValueException("Password is the same as the current value.");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getCurrency() != null) {
            if (request.getCurrency().equals(user.getCurrency())) {
                throw new SameValueException("Currency is the same as the current value.");
            }
            user.setCurrency(request.getCurrency());
        }

        userRepository.save(user);

        return mappedToUserResponse(user);
    }

    private static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


    private static UserResponse mappedToUserResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .balance(user.getBalance())
                .currency(user.getCurrency())
                .status(user.getStatus())
                .build();
    }
}
