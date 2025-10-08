package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.domain.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long>, TransactionRepository<TransferEntity> {
}
