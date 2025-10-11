package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long>, TransactionRepository<TransferEntity> {
    @Query("select t from TransferEntity t where t.byUser.email = ?1 or t.toUser.email = ?1")
    List<TransferInfoResponse> getAllTransferInfoByUserEmail(String email);
}
