package dev.smootheez.minibankapp.banking.transaction.controller;

import dev.smootheez.minibankapp.banking.transaction.http.request.*;
import dev.smootheez.minibankapp.banking.transaction.http.response.*;
import dev.smootheez.minibankapp.banking.transaction.service.*;
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

    @PostMapping("/deposit")
    public ResponseEntity<TransactionInfoResponse> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                           @Valid @RequestBody DepositRequest request)
    {
        log.info("Receiving deposit request");
        return ResponseEntity.ok(transactionService.deposit(userDetails.getUsername(), request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionInfoResponse> withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                                             @Valid @RequestBody WithdrawRequest request)
    {
        log.info("Receiving withdraw request");
        return ResponseEntity.ok(transactionService.withdraw(userDetails.getUsername(), request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionInfoResponse> transfer(@AuthenticationPrincipal UserDetails userDetails,
                                                             @Valid @RequestBody TransferRequest request)
    {
        log.info("Receiving transfer request");
        return ResponseEntity.ok(transactionService.transfer(userDetails.getUsername(), request));
    }
}
