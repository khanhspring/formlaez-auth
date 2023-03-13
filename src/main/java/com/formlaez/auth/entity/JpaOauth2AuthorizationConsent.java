package com.formlaez.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "oauth2_authorization_consent")
@IdClass(JpaOauth2AuthorizationConsent.AuthorizationConsentId.class)
public class JpaOauth2AuthorizationConsent {
	@Id
	private String registeredClientId;
	@Id
	private String principalName;
	private String authorities;

	@Getter
	@Setter
	public static class AuthorizationConsentId implements Serializable {
		private String registeredClientId;
		private String principalName;
	}
}
