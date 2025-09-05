package dev.smootheez.minibankapp.banking.transaction.entity;

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
@Table(name = "transfers", indexes = {
        @Index(name = "idx_transfers", columnList = "transfer_id")
})
public class TransferEntity extends AbstractEntity {
    @NaturalId
    @Column(name = "transfer_id", nullable = false, unique = true)
    private String transferId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    private Instant transferDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private UserEntity user;

}