package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawEntity, Long>, TransactionRepository<WithdrawEntity> {
    List<WithdrawInfoResponse> getAllWithdrawInfoByUser_Email(String email);
}
