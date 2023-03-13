package com.formlaez.auth.repository;

import com.formlaez.auth.entity.JpaOauth2AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOauth2AuthorizationConsentRepository extends JpaRepository<JpaOauth2AuthorizationConsent, String> {
    Optional<JpaOauth2AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
