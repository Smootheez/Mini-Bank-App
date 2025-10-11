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

    public Money add(Money other) {
        BigDecimal convertedAmount = CurrencyConverter.convert(other, this.currency).amount();
        return new Money(amount.add(convertedAmount), currency);
    }

    public Money subtract(Money other) {
        BigDecimal convertedAmount = CurrencyConverter.convert(other, this.currency).amount();
        BigDecimal newAmount = amount.subtract(convertedAmount);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        return new Money(newAmount, currency);
    }

    public Money convertTo(SupportedCurrency targetCurrency) {
        return CurrencyConverter.convert(this, targetCurrency);
    }
}
