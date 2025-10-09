package dev.smootheez.minibankapp.dto.response;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@SuperBuilder
@JsonPropertyOrder({"transactionId", "toName", "toEmail", "amount", "currency", "createdAt"})
public class TransferResponse extends TransactionResponse {
    private String toEmail;
    private String toName;
}
