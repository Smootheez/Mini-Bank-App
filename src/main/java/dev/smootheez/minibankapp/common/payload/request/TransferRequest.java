package dev.smootheez.minibankapp.common.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Getter
@Builder
public class TransferRequest {
    @Email(message = "Recipient email is not valid")
    @NotBlank(message = "Recipient email cannot be blank")
    private String toEmail;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Pin cannot be null")
    @NotBlank(message = "Pin cannot be blank")
    private String pin;
}
