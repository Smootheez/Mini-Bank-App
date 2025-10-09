package dev.smootheez.minibankapp.common.payload.response;

import lombok.*;
import lombok.experimental.*;

@Getter
@SuperBuilder
public class TransferResponse extends TransactionResponse {
    private String toEmail;
    private String toName;
}
