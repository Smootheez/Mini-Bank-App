package dev.smootheez.minibankapp.dto.response;

import com.fasterxml.jackson.annotation.*;
import dev.smootheez.minibankapp.enums.*;

import java.math.*;

/**
 * Projection for {@link dev.smootheez.minibankapp.entity.UserEntity}
 */
@JsonPropertyOrder({"firstName", "lastName", "email", "currency", "balance", "status"})
public interface UserInfoResponse {
    String getEmail();

    String getFirstName();

    String getLastName();

    UserStatus getStatus();

    BigDecimal getBalance();

    SupportedCurrency getCurrency();
}