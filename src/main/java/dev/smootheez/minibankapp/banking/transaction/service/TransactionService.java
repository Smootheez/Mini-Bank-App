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

    @Transactional
    public TransactionInfoResponse deposit(String email, DepositRequest request) {
        UserEntity user = loadUserByEmail(email);

        validatePin(request.getPin(), user);

        SupportedCurrency currency = user.getCurrency();
        Money userBalance = new Money(user.getBalance(), currency);
        Money depositAmount = new Money(request.getAmount(), currency);

        Money newBalance = BalanceCalculator.deposite(depositAmount, userBalance);
        user.setBalance(newBalance.amount());

        TransactionEntity transaction = new TransactionEntity();
        TransactionType transactionType = TransactionType.DEPOSIT;
        transaction.setTransactionId(TransactionIdGenerator.generate(transactionType));
        transaction.setAmount(depositAmount.amount());
        transaction.setCurrency(currency);
        transaction.setType(transactionType);
        transaction.setUser(user);

        transactionRepository.save(transaction);
        userRepository.save(user);

        log.info("Successfully deposited");
        return transactionRepository.getTransactionInfo(transaction.getTransactionId());
    }

    @Transactional
    public TransactionInfoResponse withdraw(String email, WithdrawRequest request) {
        UserEntity user = loadUserByEmail(email);

        validatePin(request.getPin(), user);

        SupportedCurrency currency = user.getCurrency();
        Money userBalance = new Money(user.getBalance(), currency);
        Money withdrawAmount = new Money(request.getAmount(), currency);

        Money newBalance = BalanceCalculator.withdraw(withdrawAmount, userBalance);
        user.setBalance(newBalance.amount());

        TransactionEntity transaction = new TransactionEntity();
        TransactionType transactionType = TransactionType.WITHDRAW;
        transaction.setTransactionId(TransactionIdGenerator.generate(transactionType));
        transaction.setAmount(withdrawAmount.amount());
        transaction.setCurrency(currency);
        transaction.setType(transactionType);
        transaction.setUser(user);

        transactionRepository.save(transaction);
        userRepository.save(user);

        log.info("Successfully withdrawn");
        return transactionRepository.getTransactionInfo(transaction.getTransactionId());
    }

    @Transactional
    public TransactionInfoResponse transfer(String email, TransferRequest request) {
        UserEntity userSender = loadUserByEmail(email);

        validatePin(request.getPin(), userSender);

        UserEntity userReceiver = loadUserByEmail(request.getReceiverEmail());

        SupportedCurrency senderCurrency = userSender.getCurrency();
        Money senderBalance = new Money(userSender.getBalance(), senderCurrency);
        Money receiverBalance = new Money(userReceiver.getBalance(), userReceiver.getCurrency());
        Money transferAmount = new Money(request.getAmount(), senderCurrency);

        BalanceCalculator.TransferResult transferResult = BalanceCalculator.transfer(transferAmount, senderBalance, receiverBalance);

        userSender.setBalance(transferResult.fromBalance().amount());
        userReceiver.setBalance(transferResult.toBalance().amount());

        TransactionEntity transaction = new TransactionEntity();
        TransactionType transactionType = TransactionType.TRANSFER;
        transaction.setTransactionId(TransactionIdGenerator.generate(transactionType));
        transaction.setReceiverEmail(userReceiver.getEmail());
        transaction.setReceiverName(userReceiver.getFirstName() + " " + userReceiver.getLastName());
        transaction.setAmount(transferAmount.amount());
        transaction.setCurrency(senderCurrency);
        transaction.setType(transactionType);
        transaction.setUser(userSender);

        transactionRepository.save(transaction); // Save transaction to the database
        userRepository.save(userSender); // Save user's sender balance to the database
        userRepository.save(userReceiver); // Save the user's receiver balance to the database

        log.info("Successfully transferred");
        return transactionRepository.getTransactionInfo(transaction.getTransactionId());
    }

    private UserEntity loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
    }

    private void validatePin(String rawPin, UserEntity user) {
        if (!passwordEncoder.matches(rawPin, user.getPin()))
            throw new InvalidCredentialsException("Invalid PIN");
    }
}
