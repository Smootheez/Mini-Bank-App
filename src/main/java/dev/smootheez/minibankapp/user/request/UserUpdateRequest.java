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

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private SupportedCurrency currency;
}
