package dev.smootheez.minibankapp.domain.enums;

import lombok.*;

import java.util.*;

public enum SupportedCurrency {
    IDR("Indonesian Rupiah"), USD("United States Dollar");

    @Getter
    private final String displayName;
    private final Currency currency;

    SupportedCurrency(String displayName) {
        this.displayName = displayName;
        this.currency = Currency.getInstance(name());
    }

    public String getCode() {
        return this.currency.getCurrencyCode();
    }

    public Currency toCurrency() {
        return this.currency;
    }
}
