package dev.smootheez.minibankapp.banking.transaction.repository;

import dev.smootheez.minibankapp.banking.transaction.http.response.*;
import dev.smootheez.minibankapp.banking.transaction.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("""
            select t.transactionId as transactionId,
                   t.receiverEmail as receiverEmail,
                   t.receiverName as receiverName,
                   t.amount as amount,
                   t.currency as currency,
                   t.type as type,
                   t.transactionDate as transactionDate
            from TransactionEntity t
            where t.transactionId = ?1
            """)
    TransactionInfoResponse getTransactionInfo(String transactionId);
}
