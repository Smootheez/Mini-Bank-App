package dev.smootheez.minibankapp.dto.response;

import lombok.*;
import lombok.experimental.*;

@Getter
@SuperBuilder
public class TransferResponse extends TransactionResponse {
    private String toEmail;
    private String toName;
}
