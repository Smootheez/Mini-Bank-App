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

    BigDecimal getAmount();

    SupportedCurrency getCurrency();

    Instant getCreatedAt();

    UserEntityInfo getByUser();

    UserEntityInfo getToUser();

    /**
     * Projection for {@link UserEntity}
     */
    interface UserEntityInfo {
        String getEmail();

        String getName();
    }
}