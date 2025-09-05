package dev.smootheez.minibankapp.banking.transaction.controller;

import dev.smootheez.minibankapp.banking.transaction.request.*;
import dev.smootheez.minibankapp.banking.transaction.response.*;
import dev.smootheez.minibankapp.banking.transaction.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody DepositRequest depositRequest)
    {
        return ResponseEntity.ok(transactionService.deposit(userDetails.getUsername(), depositRequest));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody WithdrawRequest withdrawRequest)
    {
        return ResponseEntity.ok(transactionService.withdraw(userDetails.getUsername(), withdrawRequest));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody TransferRequest transferRequest)
    {
        return ResponseEntity.ok(transactionService.transfer(userDetails.getUsername(), transferRequest));
    }
}
