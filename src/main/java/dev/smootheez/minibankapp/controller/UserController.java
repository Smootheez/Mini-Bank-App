package dev.smootheez.minibankapp.controller;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.service.*;
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

    @PutMapping("/me")
    public ApiResponseEntity<UserInfoResponse> updateUserInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserInfoUpdateRequest request) {
        String email = userDetails.getUsername();
        log.debug("Updating user info for user: {}", email);
        return ApiResponseEntity.build(HttpStatus.OK,
                "Successfully updated user info", userService.updateUserInfo(email, request));
    }
}
