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

import java.util.*;

@Slf4j
@Service
public class TransferService extends AbstractTransactionService<TransferRequest, TransferResponse> {
    private final TransferRepository transferRepository;

    protected TransferService(UserRepository userRepository, PasswordEncoder passwordEncoder, TransferRepository transferRepository) {
        super(userRepository, passwordEncoder);
        this.transferRepository = transferRepository;
    }

    public List<TransferInfoResponse> getAllTransferInfo(String email) {
        return transferRepository.getAllTransferInfoByUserEmail(email);
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

        Money transferAmount = new Money(request.getAmount(), request.getCurrency());
        var totalBalance = BalanceCalculator.transfer(
                transferAmount,
                new Money(byUser.getBalance(), byUser.getCurrency()),
                new Money(toUser.getBalance(), toUser.getCurrency()));

        byUser.setBalance(totalBalance.fromBalance().amount());
        toUser.setBalance(totalBalance.toBalance().amount());

        var transfer = new TransferEntity();
        transfer.setByUser(byUser);
        transfer.setToUser(toUser);
        transfer.setTransactionId(TransactionIdGenerator.generate("TF"));
        transfer.setAmount(transferAmount.amount());
        transfer.setCurrency(transferAmount.currency());

        transferRepository.save(transfer);

        return TransferResponse.builder()
                .transactionId(transfer.getTransactionId())
                .amount(transfer.getAmount())
                .currency(transfer.getCurrency())
                .createdAt(transfer.getCreatedAt())
                .build();
    }
}

