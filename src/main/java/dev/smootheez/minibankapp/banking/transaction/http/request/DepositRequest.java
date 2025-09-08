package dev.smootheez.minibankapp.banking.transaction.http.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;
}
