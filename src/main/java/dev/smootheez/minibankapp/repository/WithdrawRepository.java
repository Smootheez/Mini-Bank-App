package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.domain.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long>, TransactionRepository<WithdrawEntity> {
}
