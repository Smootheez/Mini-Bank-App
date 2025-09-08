package dev.smootheez.minibankapp.banking.transaction.model;

import dev.smootheez.minibankapp.banking.util.*;
import dev.smootheez.minibankapp.user.model.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

import java.math.*;
import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "receiver_email", updatable = false)
    private String receiverEmail;

    @Column(name = "receiver_name", updatable = false)
    private String receiverName;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, updatable = false)
    private SupportedCurrency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private TransactionType type;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "transaction_date", nullable = false, updatable = false)
    private Instant transactionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, updatable = false)
    private UserEntity user;
}