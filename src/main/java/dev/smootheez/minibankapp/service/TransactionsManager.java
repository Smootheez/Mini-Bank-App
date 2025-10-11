package dev.smootheez.minibankapp.service;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsManager {
    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final TransferService transferService;

    public DepositResponse deposit(String email, DepositRequest request) {
        return depositService.doTransaction(email, request);
    }

    public WithdrawResponse withdraw(String email, WithdrawRequest request) {
        return withdrawService.doTransaction(email, request);
    }

    public TransferResponse transfer(String email, TransferRequest request) {
        return transferService.doTransaction(email, request);
    }

    public List<DepositInfoResponse> getAllDepositInfo(String email) {
        return depositService.getAllDepositInfo(email);
    }

    public List<WithdrawInfoResponse> getAllWithdrawInfo(String email) {
        return withdrawService.getAllWithdrawInfo(email);
    }

    public List<TransferInfoResponse> getAllTransferInfo(String email) {
        return transferService.getAllTransferInfo(email);
    }

}

