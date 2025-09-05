package dev.smootheez.minibankapp.banking.transaction.response;

import dev.smootheez.minibankapp.banking.*;
import lombok.*;

import java.math.*;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private String transferId;
    private String receiverName;
    private String receiverEmail;
    private BigDecimal amount;
    private SupportedCurrency currency;
    private Instant transactionDate;
}
