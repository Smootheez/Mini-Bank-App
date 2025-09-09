package dev.smootheez.minibankapp.banking.transaction.service;

import dev.smootheez.minibankapp.banking.transaction.http.request.*;
import dev.smootheez.minibankapp.banking.transaction.http.response.*;
import dev.smootheez.minibankapp.banking.transaction.model.*;
import dev.smootheez.minibankapp.banking.transaction.repository.*;
import dev.smootheez.minibankapp.banking.util.*;
import dev.smootheez.minibankapp.banking.util.math.*;
import dev.smootheez.minibankapp.core.exception.*;
import dev.smootheez.minibankapp.user.model.*;
import dev.smootheez.minibankapp.user.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // === Deposit ===
    @Transactional
    public TransactionInfoResponse deposit(String email, DepositRequest request) {
        UserEntity user = loadUserByEmail(email);
        validatePin(request.getPin(), user);

        Money depositAmount = new Money(request.getAmount(), user.getCurrency());
        Money newBalance = BalanceCalculator.deposite(depositAmount, new Money(user.getBalance(), user.getCurrency()));
        user.setBalance(newBalance.amount());

        TransactionEntity transaction = buildTransaction(user, TransactionType.DEPOSIT, depositAmount);
        persistTransactionAndUsers(transaction, user);

        logTransaction("Deposit", email, depositAmount);
        return fetchTransactionInfo(transaction);
    }

    // === Withdraw ===
    @Transactional
    public TransactionInfoResponse withdraw(String email, WithdrawRequest request) {
        UserEntity user = loadUserByEmail(email);
        validatePin(request.getPin(), user);

        Money withdrawAmount = new Money(request.getAmount(), user.getCurrency());
        Money newBalance = BalanceCalculator.withdraw(withdrawAmount, new Money(user.getBalance(), user.getCurrency()));
        user.setBalance(newBalance.amount());

        TransactionEntity transaction = buildTransaction(user, TransactionType.WITHDRAW, withdrawAmount);
        persistTransactionAndUsers(transaction, user);

        logTransaction("Withdraw", email, withdrawAmount);
        return fetchTransactionInfo(transaction);
    }

    // === Transfer ===
    @Transactional
    public TransactionInfoResponse transfer(String email, TransferRequest request) {
        UserEntity sender = loadUserByEmail(email);
        validatePin(request.getPin(), sender);

        UserEntity receiver = loadUserByEmail(request.getReceiverEmail());

        Money transferAmount = new Money(request.getAmount(), sender.getCurrency());

        BalanceCalculator.TransferResult result = BalanceCalculator.transfer(
                transferAmount,
                new Money(sender.getBalance(), sender.getCurrency()),
                new Money(receiver.getBalance(), receiver.getCurrency())
        );

        sender.setBalance(result.fromBalance().amount());
        receiver.setBalance(result.toBalance().amount());

        TransactionEntity transaction = buildTransaction(
                sender,
                TransactionType.TRANSFER,
                transferAmount,
                receiver.getEmail(),
                receiver.getFirstName() + " " + receiver.getLastName()
        );

        persistTransactionAndUsers(transaction, sender, receiver);

        logTransaction(email, transferAmount, receiver.getEmail());
        return fetchTransactionInfo(transaction);
    }

    // ───────────────────────────────────────────────
    // Private Helpers
    // ───────────────────────────────────────────────

    private UserEntity loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    private void validatePin(String rawPin, UserEntity user) {
        if (!passwordEncoder.matches(rawPin, user.getPin())) {
            throw new InvalidCredentialsException("Invalid PIN");
        }
    }

    private TransactionEntity buildTransaction(UserEntity user, TransactionType type, Money amount) {
        return buildTransaction(user, type, amount, null, null);
    }

    private TransactionEntity buildTransaction(UserEntity user, TransactionType type, Money amount,
                                               String receiverEmail, String receiverName) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(TransactionIdGenerator.generate(type));
        transaction.setAmount(amount.amount());
        transaction.setCurrency(user.getCurrency());
        transaction.setType(type);
        transaction.setUser(user);

        if (type == TransactionType.TRANSFER) {
            transaction.setReceiverEmail(receiverEmail);
            transaction.setReceiverName(receiverName);
        }

        return transaction;
    }

    private void persistTransactionAndUsers(TransactionEntity transaction, UserEntity... users) {
        transactionRepository.save(transaction);
        for (UserEntity user : users) {
            userRepository.save(user);
        }
        transactionRepository.flush(); // Ensure the transaction is persisted before fetching
    }

    private TransactionInfoResponse fetchTransactionInfo(TransactionEntity transaction) {
        return transactionRepository.getTransactionInfo(transaction.getTransactionId());
    }

    private void logTransaction(String action, String senderEmail, Money amount) {
        log.info("{} successful for user {}: {} {}", action, senderEmail, amount.amount(), amount.currency());
    }

    private void logTransaction(String senderEmail, Money amount, String receiverEmail) {
        log.info("{} successful from {} to {}: {} {}", "Transfer", senderEmail, receiverEmail, amount.amount(), amount.currency());
    }
}

