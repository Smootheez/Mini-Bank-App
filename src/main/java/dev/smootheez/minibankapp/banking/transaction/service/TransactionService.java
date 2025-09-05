package dev.smootheez.minibankapp.banking.transaction.service;

import dev.smootheez.minibankapp.banking.*;
import dev.smootheez.minibankapp.banking.transaction.entity.*;
import dev.smootheez.minibankapp.banking.transaction.repository.*;
import dev.smootheez.minibankapp.banking.transaction.request.*;
import dev.smootheez.minibankapp.banking.transaction.response.*;
import dev.smootheez.minibankapp.user.entity.*;
import dev.smootheez.minibankapp.user.exception.*;
import dev.smootheez.minibankapp.user.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DepositResponse deposit(String email, DepositRequest depositRequest) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(depositRequest.getPin(), user.getPin()))
            throw new WrongCredentialException("Wrong pin");

        Money userBalance = new Money(user.getBalance(), user.getCurrency());
        Money depositBalance = new Money(depositRequest.getAmount(), depositRequest.getCurrency());

        Money totalBalance = BalanceCalculator.deposit(userBalance, depositBalance);
        user.setBalance(totalBalance.amount());

        userRepository.save(user);

        DepositEntity deposit = new DepositEntity();
        deposit.setDepositId("DEP-" + UUID.randomUUID());
        deposit.setAmount(depositRequest.getAmount());
        deposit.setCurrency(depositRequest.getCurrency());
        deposit.setUser(user);

        depositRepository.save(deposit);

        return DepositResponse.builder()
                .depositId(deposit.getDepositId())
                .amount(deposit.getAmount())
                .depositDate(deposit.getDepositDate())
                .currency(deposit.getCurrency())
                .build();
    }

    @Transactional
    public WithdrawResponse withdraw(String email, WithdrawRequest withdrawRequest) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(withdrawRequest.getPin(), user.getPin()))
            throw new WrongCredentialException("Wrong pin");

        Money userBalance = new Money(user.getBalance(), user.getCurrency());
        Money withdrawBalance = new Money(withdrawRequest.getAmount(), withdrawRequest.getCurrency());

        Money totalBalance = BalanceCalculator.withdraw(userBalance, withdrawBalance);
        user.setBalance(totalBalance.amount());

        userRepository.save(user);

        WithdrawEntity withdraw = new WithdrawEntity();
        withdraw.setWithdrawId("WDR-" + UUID.randomUUID());
        withdraw.setAmount(withdrawRequest.getAmount());
        withdraw.setCurrency(withdrawRequest.getCurrency());
        withdraw.setUser(user);

        withdrawRepository.save(withdraw);

        return WithdrawResponse.builder()
                .withdrawId(withdraw.getWithdrawId())
                .amount(withdraw.getAmount())
                .withdrawDate(withdraw.getWithdrawDate())
                .currency(withdraw.getCurrency())
                .build();
    }
}
