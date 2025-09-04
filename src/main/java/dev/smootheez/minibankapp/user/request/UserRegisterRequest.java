package dev.smootheez.minibankapp.user.request;

import dev.smootheez.minibankapp.common.banking.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Currency cannot be null")
    private SupportedCurrency currency;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
