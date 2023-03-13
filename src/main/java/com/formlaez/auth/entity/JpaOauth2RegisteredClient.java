package com.formlaez.auth.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "oauth2_registered_client")
public class JpaOauth2RegisteredClient {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String clientId;
	private Instant clientIdIssuedAt;
	private String clientSecret;
	private Instant clientSecretExpiresAt;
	private String clientName;
	private String clientAuthenticationMethods;
	private String authorizationGrantTypes;
	private String redirectUris;
	private String scopes;
	private String clientSettings;
	private String tokenSettings;
}
