package dev.smootheez.minibankapp.user.response;

import dev.smootheez.minibankapp.common.banking.*;
import dev.smootheez.minibankapp.common.enums.*;
import lombok.*;

import java.math.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
    private SupportedCurrency currency;
    private Status status;
}
