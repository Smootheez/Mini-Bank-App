package dev.smootheez.minibankapp.banking.transaction.http.response;

import dev.smootheez.minibankapp.banking.util.*;

import java.math.*;
import java.time.*;

/**
 * Projection for {@link dev.smootheez.minibankapp.banking.transaction.model.TransactionEntity}
 */
public interface TransactionInfoResponse {
    String getTransactionId();

    String getReceiverEmail();

    String getReceiverName();

    BigDecimal getAmount();

    SupportedCurrency getCurrency();

    TransactionType getType();

    Instant getTransactionDate();
}