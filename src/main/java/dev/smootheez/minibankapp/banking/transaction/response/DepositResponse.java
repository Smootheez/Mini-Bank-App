package dev.smootheez.minibankapp.banking.transaction.response;

import dev.smootheez.minibankapp.banking.*;
import lombok.*;

import java.math.*;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private String depositId;
    private BigDecimal amount;
    private Instant depositDate;
    private SupportedCurrency currency;
}
