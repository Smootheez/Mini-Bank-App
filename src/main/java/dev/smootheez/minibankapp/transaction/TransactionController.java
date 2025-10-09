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
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionSerivce transactionSerivce;

    @PostMapping("/deposit")
    public ApiResponseEntity<DepositResponse> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid @RequestBody DepositRequest request)
    {
        String email = userDetails.getUsername();
        log.debug("Deposit request received for user: {}", email);
        DepositResponse response = transactionSerivce.deposit(email, request);
        return ApiResponseEntity.build(HttpStatus.CREATED,
                "Successfully deposited " + response.getAmount() + " " + response.getCurrency() + " to your account", response);
    }
}
