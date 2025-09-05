package dev.smootheez.minibankapp.user.controller;

import dev.smootheez.minibankapp.user.request.*;
import dev.smootheez.minibankapp.user.response.*;
import dev.smootheez.minibankapp.user.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserInfo(userDetails.getUsername()));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody UserUpdateRequest userUpdateRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userDetails.getUsername(), userUpdateRequest));
    }
}
