package dev.smootheez.minibankapp.util;

import dev.smootheez.minibankapp.enums.*;
import lombok.experimental.*;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;

@UtilityClass
public class CurrencyConverter {
    private final Map<CurrencyPair, BigDecimal> exchangeRates = new ConcurrentHashMap<>();

    // Later, load this from external API or config
    static {
        exchangeRates.put(new CurrencyPair(SupportedCurrency.USD, SupportedCurrency.IDR), new BigDecimal("15000"));
        exchangeRates.put(new CurrencyPair(SupportedCurrency.IDR, SupportedCurrency.USD), new BigDecimal("0.0000666667"));
    }

    public Money convert(Money amount, SupportedCurrency toCurrency) {
        if (amount.currency().equals(toCurrency)) {
            return amount;
        }

        BigDecimal rate = getExchangeRate(amount.currency(), toCurrency);
        if (rate == null) {
            throw new UnsupportedOperationException("No exchange rate found for " + amount.currency() + " to " + toCurrency);
        }

        BigDecimal convertedAmount = amount.amount().multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return new Money(convertedAmount, toCurrency);
    }

    public void setExchangeRates(SupportedCurrency from, SupportedCurrency to, BigDecimal rate) {
        exchangeRates.put(new CurrencyPair(from, to), rate);
    }

    public BigDecimal getExchangeRate(SupportedCurrency from, SupportedCurrency to) {
        return exchangeRates.get(new CurrencyPair(from, to));
    }

    record CurrencyPair(SupportedCurrency from, SupportedCurrency to) {
    }
}
