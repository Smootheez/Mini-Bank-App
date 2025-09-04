package dev.smootheez.minibankapp.banking;

import lombok.*;

import java.util.*;

public enum SupportedCurrency {
    IDR("Indonesian Rupiah"),
    USD("US Dollar");

    @Getter
    private final String displayName;
    private final Currency currency;

    SupportedCurrency(String displayName) {
        this.displayName = displayName;
        this.currency = Currency.getInstance(name());
    }

    public String getCode() {
        return currency.getCurrencyCode();
    }

    public Currency toCurrency() {
        return currency;
    }
}

