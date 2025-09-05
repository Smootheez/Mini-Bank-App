package dev.smootheez.minibankapp.banking.transaction.request;

import dev.smootheez.minibankapp.banking.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequest {
    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Currency cannot be null")
    private SupportedCurrency currency;

    @NotNull(message = "Pin cannot be null")
    @NotBlank(message = "Pin cannot be blank")
    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;
}
