package dev.smootheez.minibankapp.banking.transaction.repository;

import dev.smootheez.minibankapp.banking.transaction.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long> {
    Optional<WithdrawEntity> findByWithdrawId(String withdrawId);
    boolean existsByWithdrawId(String withdrawId);
}
