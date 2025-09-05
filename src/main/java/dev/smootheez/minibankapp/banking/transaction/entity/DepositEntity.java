package dev.smootheez.minibankapp.banking.transaction.entity;

import dev.smootheez.minibankapp.banking.*;
import dev.smootheez.minibankapp.common.entity.*;
import dev.smootheez.minibankapp.user.entity.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.*;
import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "deposits", indexes = {
        @Index(name = "idx_deposits", columnList = "deposit_id")
})
public class DepositEntity extends AbstractEntity {
    @NaturalId
    @Column(name = "deposit_id", nullable = false, unique = true)
    private String depositId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "deposit_date", nullable = false, updatable = false)
    private Instant depositDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private SupportedCurrency currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private UserEntity user;
}