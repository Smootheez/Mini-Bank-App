package dev.smootheez.minibankapp.controller;

import dev.smootheez.minibankapp.dto.request.*;
import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.service.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionsManager transactionsManager;

    @PostMapping("/deposits")
    public ApiResponseEntity<DepositResponse> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Valid @RequestBody DepositRequest request) {
        String email = userDetails.getUsername();
        log.debug("Deposit request received for user: {}", email);

        DepositResponse response = transactionsManager.deposit(email, request);

        return ApiResponseEntity.build(
                HttpStatus.CREATED,
                "Successfully deposited " + response.getAmount() + " " + response.getCurrency() + " to your account",
                response
        );
    }

    @PostMapping("/withdraws")
    public ApiResponseEntity<WithdrawResponse> withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody WithdrawRequest request) {
        String email = userDetails.getUsername();
        log.debug("Withdraw request received for user: {}", email);

        WithdrawResponse response = transactionsManager.withdraw(email, request);

        return ApiResponseEntity.build(
                HttpStatus.OK,
                "Successfully withdrew " + response.getAmount() + " " + response.getCurrency() + " from your account",
                response
        );
    }

    @PostMapping("/transfers")
    public ApiResponseEntity<TransferResponse> transfer(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody TransferRequest request) {
        String email = userDetails.getUsername();
        log.debug("Transfer request received for user: {}", email);

        TransferResponse response = transactionsManager.transfer(email, request);

        return ApiResponseEntity.build(
                HttpStatus.ACCEPTED,
                "Successfully transferred " + response.getAmount() + " " + response.getCurrency(),
                response
        );
    }

    @GetMapping("/deposits")
    public ApiResponseEntity<List<DepositInfoResponse>> getAllDepositInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.debug("Get all deposit info request received for user: {}", email);

        return ApiResponseEntity.build(
                HttpStatus.OK,
                "Successfully retrieved all deposit info",
                transactionsManager.getAllDepositInfo(email)
        );
    }

    @GetMapping("/withdraws")
    public ApiResponseEntity<List<WithdrawInfoResponse>> getAllWithdrawInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.debug("Get all withdraw info request received for user: {}", email);

        return ApiResponseEntity.build(
                HttpStatus.OK,
                "Successfully retrieved all withdraw info",
                transactionsManager.getAllWithdrawInfo(email)
        );
    }

    @GetMapping("/transfers")
    public ApiResponseEntity<List<TransferInfoResponse>> getAllTransferInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.debug("Get all transfer info request received for user: {}", email);

        return ApiResponseEntity.build(
                HttpStatus.OK,
                "Successfully retrieved all transfer info",
                transactionsManager.getAllTransferInfo(email)
        );
    }
}

