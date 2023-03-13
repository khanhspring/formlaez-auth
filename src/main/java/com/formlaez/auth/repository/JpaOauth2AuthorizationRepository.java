package com.formlaez.auth.repository;

import com.formlaez.auth.entity.JpaOauth2Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaOauth2AuthorizationRepository extends JpaRepository<JpaOauth2Authorization, String> {
    Optional<JpaOauth2Authorization> findByState(String state);
    Optional<JpaOauth2Authorization> findByAuthorizationCodeValue(String authorizationCode);
    Optional<JpaOauth2Authorization> findByAccessTokenValue(String accessToken);
    Optional<JpaOauth2Authorization> findByRefreshTokenValue(String refreshToken);
    @Query("select a from JpaOauth2Authorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token"
    )
    Optional<JpaOauth2Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(@Param("token") String token);
}
