package dev.smootheez.minibankapp.user.http.response;

import dev.smootheez.minibankapp.banking.util.*;

import java.math.*;

/**
 * Projection for {@link dev.smootheez.minibankapp.user.model.UserEntity}
 */
public interface UserInfoResponse {
    String getEmail();

    String getFirstName();

    String getLastName();

    BigDecimal getBalance();

    SupportedCurrency getCurrency();
}