package dev.smootheez.minibankapp.banking.transaction.request;

import dev.smootheez.minibankapp.banking.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "Receiver email is required, it can't be null")
    @NotBlank(message = "Receiver email is required, it can't be blank")
    private String receiverEmail;

    @NotNull(message = "Amount is required, it can't be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @NotNull(message = "Currency is required, it can't be null")
    private SupportedCurrency currency;

    @NotNull(message = "Pin is required, it can't be null")
    @NotBlank(message = "Pin is required, it can't be blank")
    private String pin;
}
