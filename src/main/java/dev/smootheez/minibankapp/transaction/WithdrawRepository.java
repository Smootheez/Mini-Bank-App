package dev.smootheez.minibankapp.transaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long>, TransactionRepository<WithdrawEntity> {
}
