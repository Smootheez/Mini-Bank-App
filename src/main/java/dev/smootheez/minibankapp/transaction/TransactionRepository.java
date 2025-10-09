package dev.smootheez.minibankapp.transaction;

import java.util.*;

public interface TransactionRepository<T> {
    Optional<T> findByTransactionId(String transactionId);

    boolean existsByTransactionId(String transactionId);
}
