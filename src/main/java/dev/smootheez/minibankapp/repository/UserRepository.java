package dev.smootheez.minibankapp.repository;

import dev.smootheez.minibankapp.dto.response.*;
import dev.smootheez.minibankapp.entity.UserEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select u from UserEntity u where u.email = ?1")
    UserInfoResponse getUserInfoByEmail(String email);
}
