package dev.smootheez.minibankapp.controller;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.service.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ApiResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.debug("Getting user info for user: {}", email);
        return ApiResponseEntity.build(HttpStatus.OK,
                "Successfully retrieved user info", userService.getUserInfo(email));
    }

    @PutMapping("/me/password")
    public ApiResponseEntity<Object> updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                                    @Valid @RequestBody UpdatePasswordRequest request) {
        String email = userDetails.getUsername();
        log.debug("Updating password for user: {}", email);
        userService.updatePassword(email, request);
        return ApiResponseEntity.build(HttpStatus.OK, "Password updated successfully");
    }

    @PutMapping("/me/pin")
    public ApiResponseEntity<Object> updatePin(@AuthenticationPrincipal UserDetails userDetails,
                                               @Valid @RequestBody UpdatePinRequest request) {
        String email = userDetails.getUsername();
        log.debug("Updating PIN for user: {}", email);
        userService.updatePin(email, request);
        return ApiResponseEntity.build(HttpStatus.OK, "PIN updated successfully");
    }
}
