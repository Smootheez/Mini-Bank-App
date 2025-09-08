package dev.smootheez.minibankapp.user.http.request;

import dev.smootheez.minibankapp.banking.util.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @Email(message = "Email must be valid")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;

    @NotNull(message = "Currency cannot be null")
    private SupportedCurrency currency;
}
