package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.common.payload.*;
import dev.smootheez.minibankapp.common.payload.request.*;
import dev.smootheez.minibankapp.common.payload.response.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposits")
    public ApiResponseEntity<DepositResponse> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Valid @RequestBody DepositRequest request) {
        String email = userDetails.getUsername();
        log.debug("Deposit request received for user: {}", email);
        DepositResponse response = transactionService.deposit(email, request);
        return ApiResponseEntity.build(HttpStatus.CREATED,
                "Successfully deposited " + response.getAmount() + " " + response.getCurrency() + " to your account", response);
    }

    @PostMapping("/withdraws")
    public ApiResponseEntity<WithdrawResponse> withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody WithdrawRequest request) {
        String email = userDetails.getUsername();
        log.debug("Withdraw request received for user: {}", email);
        WithdrawResponse response = transactionService.withdraw(email, request);
        return ApiResponseEntity.build(HttpStatus.OK,
                "Successfully withdrew " + response.getAmount() + " " + response.getCurrency() + " from your account", response);
    }

    @PostMapping("/transfers")
    public ApiResponseEntity<TransferResponse> transfer(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody TransferRequest request) {
        String email = userDetails.getUsername();
        log.debug("Transfer request received for user: {}", email);
        TransferResponse response = transactionService.transfer(email, request);
        return ApiResponseEntity.build(HttpStatus.ACCEPTED,
                "Successfully transferred " + response.getAmount() + " " + response.getCurrency() + " to " + response.getToEmail(), response);
    }

}
