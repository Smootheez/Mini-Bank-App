package dev.smootheez.minibankapp.common.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Getter
@Builder
public class DepositRequest {
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
}
