package dev.smootheez.minibankapp.banking;

import java.math.*;
import java.util.*;

public record Money(BigDecimal amount, SupportedCurrency currency) {
    public Money {
        Objects.requireNonNull(amount, "Amount cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money add(Money other) {
        checkSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        checkSameCurrency(other);
        BigDecimal newAmount = amount.subtract(other.amount);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        return new Money(newAmount, currency);
    }

    public boolean isGreaterOrEqual(Money other) {
        checkSameCurrency(other);
        return amount.compareTo(other.amount) >= 0;
    }

    private void checkSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies mismatch " + currency + " != " + other.currency);
        }
    }
}
