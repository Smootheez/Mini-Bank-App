package dev.smootheez.minibankapp.banking.transaction.response;

import dev.smootheez.minibankapp.banking.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private String email;
    private BigDecimal amount;
    private SupportedCurrency currency;
}
