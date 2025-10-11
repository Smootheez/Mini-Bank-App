package dev.smootheez.minibankapp.util;

import lombok.experimental.*;

@UtilityClass
public class BalanceCalculator {
    public Money deposit(Money amount, Money balance) {
        return balance.add(amount);
    }

    public Money withdraw(Money amount, Money balance) {
        return balance.subtract(amount);
    }

    public TransferResult transfer(Money amount, Money fromBalance, Money toBalance) {
        Money newFromBalance = withdraw(amount, fromBalance);
        Money convertedAmount = amount.convertTo(toBalance.currency());
        Money newToBalance = deposit(convertedAmount, toBalance);
        return new TransferResult(newFromBalance, newToBalance);
    }

    public record TransferResult(Money fromBalance, Money toBalance) { }
}
