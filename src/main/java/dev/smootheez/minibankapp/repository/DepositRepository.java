package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Long>, TransactionRepository<DepositEntity> {
    List<DepositInfoResponse> getAllDepositInfoByUser_Email(String email);
}
