package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.common.enums.*;
import dev.smootheez.minibankapp.common.exception.*;
import dev.smootheez.minibankapp.common.payload.request.*;
import dev.smootheez.minibankapp.common.payload.response.*;
import dev.smootheez.minibankapp.common.util.*;
import dev.smootheez.minibankapp.common.util.math.*;
import dev.smootheez.minibankapp.user.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.math.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionSerivce {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;

    @Transactional
    public DepositResponse deposit(String username, DepositRequest request) {
        log.debug("Processing Deposit request  for user: {}", username);

        var byUser = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("User not found with email: " + username)
        );

        validatePin(request.getPin(), byUser);

        SupportedCurrency currency = byUser.getCurrency();
        Money depositAmount = new Money(request.getAmount(), currency);
        Money totalBalance = BalanceCalculator.deposite(depositAmount,
                new Money(byUser.getBalance(), currency));

        byUser.setBalance(totalBalance.amount());

        var deposit = new DepositEntity();
        deposit.setUser(byUser);
        deposit.setTransactionId(TransactionIdGenerator.generate("DP"));
        deposit.setByEmail(byUser.getEmail());
        deposit.setByName(byUser.getFirstName() + " " + byUser.getLastName());
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

    @Transactional
    public WithdrawResponse withdraw(String username, WithdrawRequest request) {
        log.debug("Processing Withdraw request for user: {}", username);

        var byUser = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("User not found with email: " + username)
        );

        validatePin(request.getPin(), byUser);

        SupportedCurrency currency = byUser.getCurrency();
        Money withdrawAmount = new Money(request.getAmount(), currency);
        Money totalBalance = BalanceCalculator.withdraw(withdrawAmount,
                new Money(byUser.getBalance(), currency));

        byUser.setBalance(totalBalance.amount());

        var withdraw = new WithdrawEntity();
        withdraw.setUser(byUser);
        withdraw.setTransactionId(TransactionIdGenerator.generate("WD"));
        withdraw.setByEmail(byUser.getEmail());
        withdraw.setByName(byUser.getFirstName() + " " + byUser.getLastName());
        withdraw.setAmount(withdrawAmount.amount());
        withdraw.setCurrency(currency);

        withdrawRepository.save(withdraw);

        return WithdrawResponse.builder()
                .transactionId(withdraw.getTransactionId())
                .amount(withdraw.getAmount())
                .currency(withdraw.getCurrency())
                .createdAt(withdraw.getCreatedAt())
                .build();
    }

    private void validatePin(String rawPin, UserEntity byUser) {
        if (!passwordEncoder.matches(rawPin, byUser.getPin()))
            throw new BadCredentialException("Invalid rawPin");
    }
}
