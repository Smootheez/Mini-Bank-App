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
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DepositResponse deposit(String email, DepositRequest depositRequest) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        Money currentBalance = new Money(user.getBalance(), user.getCurrency());
        Money newBalance = new Money(depositRequest.getAmount(), depositRequest.getCurrency());

        currentBalance.add(newBalance);

        userRepository.save(user);

        TransactionEntity transaction = new TransactionEntity();

        return DepositResponse.builder().build();
    }
}
