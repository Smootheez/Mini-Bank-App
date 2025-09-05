package dev.smootheez.minibankapp.banking.transaction.repository;

import dev.smootheez.minibankapp.banking.transaction.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long> {
    Optional<DepositEntity> findByDepositId(String depositId);
    boolean existsByDepositId(String depositId);
}
