package com.formlaez.auth.repository;

import com.formlaez.auth.common.enumeration.UserStatus;
import com.formlaez.auth.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUser, String> {
    Optional<JpaUser> findByUsernameAndStatus(String username, UserStatus status);
    Optional<JpaUser> findByUsernameAndStatusIn(String username, List<UserStatus> statuses);
}
