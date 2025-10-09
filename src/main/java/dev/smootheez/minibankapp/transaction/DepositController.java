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
@RequestMapping("/api/v1/transaction/deposit")
public class DepositController {
    private final DepositService depositService;

    @PostMapping
    public ApiResponseEntity<DepositResponse> doTransaction(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid @RequestBody DepositRequest request)
    {
        String email = userDetails.getUsername();
        log.debug("Deposit request received for user: {}", email);
        DepositResponse response = depositService.doTransaction(email, request);
        return ApiResponseEntity.build(HttpStatus.CREATED,
                "Successfully " + response.getAmount() + " " + response.getCurrency() + " to your account", response);
    }
}
