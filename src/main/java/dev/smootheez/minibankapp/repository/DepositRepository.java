package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.domain.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long>, TransactionRepository<DepositEntity> {
}
