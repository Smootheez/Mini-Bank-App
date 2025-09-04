package dev.smootheez.minibankapp.user.request;

import dev.smootheez.minibankapp.common.banking.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Size(min = 6, message = "New password must be at least 6 characters long")
    private String newPassword;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;

    private SupportedCurrency currency;
}
