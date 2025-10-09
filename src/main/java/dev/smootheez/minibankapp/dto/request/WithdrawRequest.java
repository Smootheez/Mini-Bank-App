package dev.smootheez.minibankapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Getter
@Builder
public class WithdrawRequest {
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Pin cannot be null")
    @NotBlank(message = "Pin cannot be blank")
    private String pin;
}
