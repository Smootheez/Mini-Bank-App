package dev.smootheez.minibankapp.user.entity;

import dev.smootheez.minibankapp.banking.*;
import dev.smootheez.minibankapp.banking.transaction.entity.*;
import dev.smootheez.minibankapp.common.entity.*;
import dev.smootheez.minibankapp.common.enums.*;
import dev.smootheez.minibankapp.user.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
public class UserEntity extends AbstractEntity {
    @NaturalId(mutable = true)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private SupportedCurrency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferEntity> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepositEntity> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithdrawEntity> withdraws = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.status = Status.ACTIVE;
    }
}