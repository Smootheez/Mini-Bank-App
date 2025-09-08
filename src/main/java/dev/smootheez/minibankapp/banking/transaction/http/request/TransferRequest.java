package dev.smootheez.minibankapp.banking.transaction.http.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String receiverEmail;

    @Pattern(regexp = "^\\d{6}$", message = "Pin must be 6 digits long")
    private String pin;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;
}
