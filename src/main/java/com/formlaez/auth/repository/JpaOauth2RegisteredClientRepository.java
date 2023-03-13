package com.formlaez.auth.repository;

import com.formlaez.auth.entity.JpaOauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOauth2RegisteredClientRepository extends JpaRepository<JpaOauth2RegisteredClient, String> {
    Optional<JpaOauth2RegisteredClient> findByClientId(String clientId);
}
