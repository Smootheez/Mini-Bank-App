package dev.smootheez.minibankapp.transaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long>, TransactionRepository<DepositEntity> {
}
