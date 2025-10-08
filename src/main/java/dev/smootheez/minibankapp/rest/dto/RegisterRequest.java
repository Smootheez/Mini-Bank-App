package dev.smootheez.minibankapp.rest.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
public class RegisterRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;

    @NotBlank(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;
}