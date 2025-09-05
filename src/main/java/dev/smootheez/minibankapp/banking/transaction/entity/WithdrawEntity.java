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
@Table(name = "withdraws", indexes = {
        @Index(name = "idx_withdraws", columnList = "withdraw_id")
})
public class WithdrawEntity extends AbstractEntity {
    @NaturalId
    @Column(name = "withdraw_id", nullable = false, unique = true)
    private String withdrawId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private SupportedCurrency currency;

    @CreationTimestamp
    @Column(name = "withdraw_date", nullable = false, updatable = false)
    private Instant withdrawDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private UserEntity user;
}