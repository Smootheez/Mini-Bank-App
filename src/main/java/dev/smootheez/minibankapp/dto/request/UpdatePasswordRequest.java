package dev.smootheez.minibankapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
public class UpdatePasswordRequest {
    @NotNull(message = "Old password cannot be null")
    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @NotNull(message = "New password cannot be null")
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;

    @NotNull(message = "Confirm new password cannot be null")
    @NotBlank(message = "Confirm new password cannot be blank")
    private String confirmNewPassword;
}
