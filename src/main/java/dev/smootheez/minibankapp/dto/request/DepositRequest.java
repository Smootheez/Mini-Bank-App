package dev.smootheez.minibankapp.dto.request;

import dev.smootheez.minibankapp.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Getter
@Builder
public class DepositRequest {
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Currency cannot be null")
    private SupportedCurrency currency;

    @NotNull(message = "Pin cannot be null")
    @NotBlank(message = "Pin cannot be blank")
    private String pin;
}
