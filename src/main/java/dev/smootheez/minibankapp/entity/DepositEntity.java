package dev.smootheez.minibankapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("DEPOSIT")
@Table(name = "deposits")
public class DepositEntity extends TransactionEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;
}
