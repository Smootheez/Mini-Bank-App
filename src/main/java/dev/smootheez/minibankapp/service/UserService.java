package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.repository.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(String email) {
        return userRepository.getUserInfoByEmail(email);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(String email, UserInfoUpdateRequest request) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );

        String firstName = request.getFirstName();
        if (firstName != null) user.setFirstName(firstName);

        String lastName = request.getLastName();
        if (lastName != null) user.setLastName(lastName);

        return getUserInfo(email);
    }
}
