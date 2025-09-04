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
    private String transactionId;
    private String senderName;
    private String senderEmail;
    private String receiverName;
    private String receiverEmail;
    private BigDecimal amount;
    private SupportedCurrency currency;
    private Instant transactionDate;
}
