package dev.smootheez.minibankapp.user.controller;

import dev.smootheez.minibankapp.user.http.request.*;
import dev.smootheez.minibankapp.user.http.response.*;
import dev.smootheez.minibankapp.user.service.*;
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
    public ResponseEntity<UserInfoResponse> info(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Receiving user info request");
        return ResponseEntity.ok(userService.info(userDetails.getUsername()));
    }

    @PutMapping("/update")
    public ResponseEntity<UserInfoResponse> update(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody UserUpdateRequest request)
    {
        log.info("Receiving user update request");
        return ResponseEntity.ok(userService.update(userDetails.getUsername(), request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails,
                                         @Valid @RequestBody UserDeleteRequest request)
    {
        log.info("Receiving user delete request");
        userService.delete(userDetails.getUsername(), request);
        return ResponseEntity.ok("User deleted successfully");
    }
}
