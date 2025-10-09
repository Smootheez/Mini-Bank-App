package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.user.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("WITHDRAW")
@Table(name = "withdraws")
public class WithdrawEntity extends TransactionEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false, updatable = false)
    private UserEntity user;

}
