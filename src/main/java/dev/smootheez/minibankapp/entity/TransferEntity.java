package dev.smootheez.minibankapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("TRANSFER")
@Table(name = "transfers")
public class TransferEntity extends TransactionEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "by_user_id", nullable = false, updatable = false)
    private UserEntity byUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_user_id", nullable = false, updatable = false)
    private UserEntity toUser;
}
