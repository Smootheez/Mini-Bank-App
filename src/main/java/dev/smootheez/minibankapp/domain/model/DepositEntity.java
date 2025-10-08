package dev.smootheez.minibankapp.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("DEPOSIT")
@Table(name = "deposits")
public class DepositEntity extends TransactionEntity {
}
