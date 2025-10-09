package dev.smootheez.minibankapp.transaction;

import dev.smootheez.minibankapp.common.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.*;
import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transactions", columnList = "transaction_id, by_email")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "by_email", nullable = false, updatable = false)
    private String byEmail;

    @Column(name = "by_name", nullable = false, updatable = false)
    private String byName;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "currency", nullable = false)
    private SupportedCurrency currency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}