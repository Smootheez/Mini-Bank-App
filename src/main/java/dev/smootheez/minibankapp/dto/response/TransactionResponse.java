package dev.smootheez.minibankapp.dto.response;

import dev.smootheez.minibankapp.enums.*;
import lombok.*;
import lombok.experimental.*;

import java.math.*;
import java.time.*;

@Getter
@SuperBuilder
public class TransactionResponse {
    private String transactionId;
    private BigDecimal amount;
    private SupportedCurrency currency;
    private Instant createdAt;
}
