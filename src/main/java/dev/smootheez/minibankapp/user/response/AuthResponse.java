package dev.smootheez.minibankapp.user.response;

import dev.smootheez.minibankapp.user.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String token;
    private UserRole role;
}
