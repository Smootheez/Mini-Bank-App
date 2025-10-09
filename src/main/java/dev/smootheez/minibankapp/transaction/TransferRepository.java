package dev.smootheez.minibankapp.transaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long>, TransactionRepository<TransferEntity> {
}
