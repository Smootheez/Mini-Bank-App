package dev.smootheez.minibankapp.banking.transaction.response;

import dev.smootheez.minibankapp.banking.*;
import lombok.*;

import java.math.*;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawResponse {
    private String withdrawId;
    private BigDecimal amount;
    private Instant withdrawDate;
    private SupportedCurrency currency;
}
