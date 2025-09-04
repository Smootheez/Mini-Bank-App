package dev.smootheez.minibankapp.banking.transaction.repository;

import dev.smootheez.minibankapp.banking.transaction.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByTransactionId(String transactionId);
    boolean existsByTransactionId(String transactionId);
}
