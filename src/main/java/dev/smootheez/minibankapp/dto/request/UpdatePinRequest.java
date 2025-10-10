package dev.smootheez.minibankapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
public class UpdatePinRequest {
    @NotNull(message = "Old PIN cannot be null")
    @NotBlank(message = "Old PIN cannot be blank")
    private String oldPin;

    @Pattern(regexp = "^\\d{6}$", message = "New PIN must be 6 digits long")
    private String newPin;

    @NotNull(message = "Confirm new PIN cannot be null")
    @NotBlank(message = "Confirm new PIN cannot be blank")
    private String confirmNewPin;
}
