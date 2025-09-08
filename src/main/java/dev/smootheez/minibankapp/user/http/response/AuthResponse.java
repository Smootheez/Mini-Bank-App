package dev.smootheez.minibankapp.user.http.response;

import dev.smootheez.minibankapp.user.model.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String token;
    private Role role;
}
