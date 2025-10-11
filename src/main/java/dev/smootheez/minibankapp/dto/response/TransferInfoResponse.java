package dev.smootheez.minibankapp.dto.response;

import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.enums.*;

import java.math.*;
import java.time.*;

/**
 * Projection for {@link TransferEntity}
 */
public interface TransferInfoResponse {
    String getTransactionId();

    String getByEmail();

    String getByName();

    String getToEmail();

    String getToName();

    BigDecimal getAmount();

    SupportedCurrency getCurrency();

    Instant getCreatedAt();
}