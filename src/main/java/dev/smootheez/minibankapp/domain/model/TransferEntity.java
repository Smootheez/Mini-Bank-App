package dev.smootheez.minibankapp.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRANSFER")
@Table(name = "transfers")
public class TransferEntity extends TransactionEntity {
    @Column(name = "to_email", nullable = false, updatable = false)
    private String toEmail;

    @Column(name = "to_name", nullable = false, updatable = false)
    private String toName;
}
