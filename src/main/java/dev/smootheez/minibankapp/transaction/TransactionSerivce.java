package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.common.enums.*;
import dev.smootheez.minibankapp.common.payload.request.*;
import dev.smootheez.minibankapp.common.payload.response.*;
import dev.smootheez.minibankapp.common.util.*;
import dev.smootheez.minibankapp.common.util.math.*;
import dev.smootheez.minibankapp.user.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionSerivce {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;

    @Transactional
    public DepositResponse deposit(String username, DepositRequest request) {
        var fromUser = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("User not found with email: " + username)
        );

        SupportedCurrency currency = fromUser.getCurrency();
        Money depositAmount = BalanceCalculator.deposite(new Money(request.getAmount(), currency),
                new Money(fromUser.getBalance(), currency));

        fromUser.setBalance(depositAmount.amount());

        var deposit = new DepositEntity();
        deposit.setUser(fromUser);
        deposit.setTransactionId(TransactionIdGenerator.generate("DPT"));
        deposit.setByEmail(fromUser.getEmail());
        deposit.setByName(fromUser.getFirstName() + " " + fromUser.getLastName());
        deposit.setAmount(depositAmount.amount());
        deposit.setCurrency(currency);

        depositRepository.save(deposit);

        return DepositResponse.builder()
                .transactionId(deposit.getTransactionId())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .createdAt(deposit.getCreatedAt())
                .build();
    }
}
