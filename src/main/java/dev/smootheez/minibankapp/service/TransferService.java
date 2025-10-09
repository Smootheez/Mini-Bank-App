package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.enums.*;
import dev.smootheez.minibankapp.exception.*;
import dev.smootheez.minibankapp.repository.*;
import dev.smootheez.minibankapp.util.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Slf4j
@Service
public class TransferService extends AbstractTransactionService<TransferRequest, TransferResponse> {
    private final TransferRepository transferRepository;

    protected TransferService(UserRepository userRepository, PasswordEncoder passwordEncoder, TransferRepository transferRepository) {
        super(userRepository, passwordEncoder);
        this.transferRepository = transferRepository;
    }

    @Override
    public TransferResponse doTransaction(String email, TransferRequest request) {
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
}

