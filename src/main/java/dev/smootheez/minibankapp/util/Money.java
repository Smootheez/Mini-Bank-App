package dev.smootheez.minibankapp.util;

import dev.smootheez.minibankapp.enums.*;
import dev.smootheez.minibankapp.exception.*;
import lombok.*;

import java.math.*;

public record Money(@NonNull BigDecimal amount, @NonNull SupportedCurrency currency) {
    public Money {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money add(Money money) {
        validateCurrency(money);
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(Money money) {
        validateCurrency(money);
        BigDecimal newAmount = amount.subtract(money.amount);
        if (amount.compareTo(money.amount) < 0) {
            throw new InfsufficientFundsException("Insufficient funds");
        }
        return new Money(newAmount, currency);
    }

    private void validateCurrency(Money money) {
        if (!currency.equals(money.currency)) {
            throw new CurrencyMismatchException("Currencies must be the same");
        }
    }
}
