package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.common.enums.*;
import dev.smootheez.minibankapp.common.payload.request.*;
import dev.smootheez.minibankapp.common.payload.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {
    private final DepositRepository depositRepository;

    @Transactional
    public DepositResponse doTransaction(String username, DepositRequest request) {
        return DepositResponse.builder()
                .transactionId("deposit-id")
                .amount(request.getAmount())
                .currency(SupportedCurrency.IDR)
                .createdAt(Instant.now())
                .build();
    }
}
