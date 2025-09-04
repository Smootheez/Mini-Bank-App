package dev.smootheez.minibankapp.common.banking;

import lombok.experimental.*;

@UtilityClass
public final class BalanceCalculator {
    public Money deposit(Money balance, Money amount) {
        return balance.add(amount);
    }

    public Money withdraw(Money balance, Money amount) {
        return balance.subtract(amount);
    }

    public TransferResult transfer(Money fromBalance, Money toBalance, Money amount) {
        Money newFromBalance = withdraw(fromBalance, amount);
        Money newToBalance = deposit(toBalance, amount);
        return new TransferResult(newFromBalance, newToBalance);
    }

    public record TransferResult(Money fromBalance, Money toBalance) {}
}
