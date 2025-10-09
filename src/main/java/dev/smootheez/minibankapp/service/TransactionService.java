package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.enums.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
import dev.smootheez.minibankapp.util.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepositRepository depositRepository;
    private final WithdrawRepository withdrawRepository;
    private final TransferRepository transferRepository;

    public static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";

    @Transactional
    public DepositResponse deposit(String email, DepositRequest request) {
        log.debug("Processing Deposit request  for user: {}", email);

        var byUser = getUserEntity(email, USER_NOT_FOUND_WITH_EMAIL);

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

    private UserEntity getUserEntity(String email, String userNotFoundWithEmail) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(userNotFoundWithEmail + email)
        );
    }

    @Transactional
    public WithdrawResponse withdraw(String email, WithdrawRequest request) {
        log.debug("Processing Withdraw request for user: {}", email);

        var byUser = getUserEntity(email, USER_NOT_FOUND_WITH_EMAIL);

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

    @Transactional
    public TransferResponse transfer(String email, TransferRequest request) {
        log.debug("Processing Transfer request for user: {}", email);

        var byUser = getUserEntity(email, USER_NOT_FOUND_WITH_EMAIL);

        validatePin(request.getPin(), byUser);

        String toEmail = request.getToEmail();
        var toUser = getUserEntity(toEmail, "Recipient user not found with email: ");

        if (email.equals(toEmail))
            throw new InvalidTransactionException("You cannot transfer to yourself");

        SupportedCurrency currency = byUser.getCurrency();
        Money transferAmount = new Money(request.getAmount(), currency);
        var totalBalance = BalanceCalculator.transfer(
                transferAmount,
                new Money(byUser.getBalance(), currency),
                new Money(toUser.getBalance(), currency));

        byUser.setBalance(totalBalance.fromBalance().amount());
        toUser.setBalance(totalBalance.toBalance().amount());

        var transfer = new TransferEntity();
        transfer.setUser(byUser);
        transfer.setTransactionId(TransactionIdGenerator.generate("TF"));
        transfer.setByEmail(byUser.getEmail());
        transfer.setByName(byUser.getFirstName() + " " + byUser.getLastName());
        transfer.setToEmail(toUser.getEmail());
        transfer.setToName(toUser.getFirstName() + " " + toUser.getLastName());
        transfer.setAmount(transferAmount.amount());
        transfer.setCurrency(currency);

        transferRepository.save(transfer);

        return TransferResponse.builder()
                .transactionId(transfer.getTransactionId())
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .createdAt(transfer.getCreatedAt())
                .toEmail(transfer.getToEmail())
                .toName(transfer.getToName())
                .build();
    }

    private void validatePin(String rawPin, UserEntity byUser) {
        if (!passwordEncoder.matches(rawPin, byUser.getPin()))
            throw new BadCredentialException("Invalid rawPin");
    }
}
