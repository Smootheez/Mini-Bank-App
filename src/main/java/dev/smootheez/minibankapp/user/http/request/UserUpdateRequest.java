package dev.smootheez.minibankapp.user.http.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String newFirstName;
    private String newLastName;

    @Email(message = "Invalid email format")
    private String newEmail;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String newPin;
}
