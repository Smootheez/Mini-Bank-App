package dev.smootheez.minibankapp.dto.response;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@SuperBuilder
@JsonPropertyOrder({"transactionId", "amount", "currency", "createdAt"})
public class TransferResponse extends TransactionResponse {
}
