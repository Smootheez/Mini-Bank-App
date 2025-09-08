package dev.smootheez.minibankapp.user.repository;

import dev.smootheez.minibankapp.user.http.response.*;
import dev.smootheez.minibankapp.user.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
            select u.email as email,
                   u.firstName as firstName,
                   u.lastName as lastName,
                   u.balance as balance,
                   u.currency as currency
            from UserEntity u
            where u.email = ?1
            """)
    UserInfoResponse getUserInfo(String email);
}
