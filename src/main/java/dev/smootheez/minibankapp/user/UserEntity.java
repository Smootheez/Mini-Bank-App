package dev.smootheez.minibankapp.user;

import dev.smootheez.minibankapp.common.enums.*;
import dev.smootheez.minibankapp.transaction.*;
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
@Table(name = "users", indexes = {
        @Index(name = "idx_users", columnList = "email")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId(mutable = true)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "currency", nullable = false)
    private SupportedCurrency currency;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepositEntity> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithdrawEntity> withdraws = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.balance == null) this.balance = BigDecimal.ZERO;
        if (this.role == null) this.role = UserRole.USER;
        if (this.status == null) this.status = UserStatus.ACTIVE;
    }
}