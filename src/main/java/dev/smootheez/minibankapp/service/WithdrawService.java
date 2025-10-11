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

@Slf4j
@Service
public class WithdrawService extends AbstractTransactionService<WithdrawRequest, WithdrawResponse> {
    private final WithdrawRepository withdrawRepository;

    protected WithdrawService(UserRepository userRepository, PasswordEncoder passwordEncoder, WithdrawRepository withdrawRepository) {
        super(userRepository, passwordEncoder);
        this.withdrawRepository = withdrawRepository;
    }

    public java.util.List<WithdrawInfoResponse> getAllWithdrawInfo(String email) {
        log.debug("Processing get all withdraw info request for user: {}", email);
        return withdrawRepository.getAllWithdrawInfoByUser_Email(email);
    }

    @Override
    public WithdrawResponse doTransaction(String email, WithdrawRequest request) {
        log.debug("Processing Withdraw request for user: {}", email);

        var byUser = getUserEntity(email, USER_NOT_FOUND_WITH_EMAIL);
        validatePin(request.getPin(), byUser);

        Money withdrawAmount = new Money(request.getAmount(), request.getCurrency());
        Money totalBalance = BalanceCalculator.withdraw(withdrawAmount,
                new Money(byUser.getBalance(), byUser.getCurrency()));

        byUser.setBalance(totalBalance.amount());

        var withdraw = new WithdrawEntity();
        withdraw.setUser(byUser);
        withdraw.setTransactionId(TransactionIdGenerator.generate("WD"));
        withdraw.setByEmail(byUser.getEmail());
        withdraw.setByName(byUser.getFirstName() + " " + byUser.getLastName());
        withdraw.setAmount(withdrawAmount.amount());
        withdraw.setCurrency(withdrawAmount.currency());

        withdrawRepository.save(withdraw);

        return WithdrawResponse.builder()
                .transactionId(withdraw.getTransactionId())
                .amount(withdraw.getAmount())
                .currency(withdraw.getCurrency())
                .createdAt(withdraw.getCreatedAt())
                .build();
    }
}

