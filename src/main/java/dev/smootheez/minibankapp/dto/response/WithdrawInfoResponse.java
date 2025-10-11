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

    BigDecimal getAmount();

    SupportedCurrency getCurrency();

    Instant getCreatedAt();

    UserEntityInfo getUser();

    /**
     * Projection for {@link UserEntity}
     */
    interface UserEntityInfo {
        String getEmail();

        String getName();
    }
}