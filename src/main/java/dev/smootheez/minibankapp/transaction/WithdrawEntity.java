package dev.smootheez.minibankapp.transaction;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("WITHDRAW")
@Table(name = "withdraws")
public class WithdrawEntity extends TransactionEntity {
}
