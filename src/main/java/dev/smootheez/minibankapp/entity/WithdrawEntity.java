package dev.smootheez.minibankapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("WITHDRAW")
@Table(name = "withdraws")
public class WithdrawEntity extends TransactionEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

}
