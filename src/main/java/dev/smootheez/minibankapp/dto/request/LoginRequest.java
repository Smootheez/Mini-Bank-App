package dev.smootheez.minibankapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
public class LoginRequest {
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
