package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import dev.smootheez.minibankapp.enums.*;
import dev.smootheez.minibankapp.repository.*;
import dev.smootheez.minibankapp.util.*;
import lombok.extern.slf4j.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Service
public class DepositService extends AbstractTransactionService<DepositRequest, DepositResponse> {
    private final DepositRepository depositRepository;

    protected DepositService(UserRepository userRepository, PasswordEncoder passwordEncoder, DepositRepository depositRepository) {
        super(userRepository, passwordEncoder);
        this.depositRepository = depositRepository;
    }

    public List<DepositInfoResponse> getAllDepositInfo(String email) {
        return depositRepository.getAllDepositInfoByUser_Email(email);
    }

    @Override
    public DepositResponse doTransaction(String email, DepositRequest request) {
        log.debug("Processing Deposit request for user: {}", email);

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
}

