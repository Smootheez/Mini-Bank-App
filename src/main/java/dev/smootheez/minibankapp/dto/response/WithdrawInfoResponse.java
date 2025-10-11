package dev.smootheez.minibankapp.dto.response;

import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.enums.*;

import java.math.*;
import java.time.*;

/**
 * Projection for {@link WithdrawEntity}
 */
public interface WithdrawInfoResponse {
    String getTransactionId();

    String getByEmail();

    String getByName();

    BigDecimal getAmount();

    SupportedCurrency getCurrency();

    Instant getCreatedAt();
}