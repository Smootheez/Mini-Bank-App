package dev.smootheez.minibankapp.banking.util.math;

import dev.smootheez.minibankapp.banking.util.*;
import lombok.experimental.*;

@UtilityClass
public class BalanceCalculator {
    public Money deposite(Money amount, Money balance) {
        return balance.add(amount);
    }

    public Money withdraw(Money amount, Money balance) {
        return balance.subtract(amount);
    }

    public TransferResult transfer(Money amount, Money fromBalance, Money toBalance) {
        Money newFromBalance = withdraw(amount, fromBalance);
        Money newToBalance = deposite(amount, toBalance);
        return new TransferResult(newFromBalance, newToBalance);
    }

    public record TransferResult(Money fromBalance, Money toBalance) { }
}
