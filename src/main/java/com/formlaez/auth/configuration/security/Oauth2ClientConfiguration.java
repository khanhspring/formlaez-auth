package com.formlaez.auth.configuration.security;

import com.formlaez.auth.configuration.property.AppOAuth2ClientProperties;
import com.formlaez.auth.configuration.property.OAuth2Client;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Oauth2ClientConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final AppOAuth2ClientProperties appOAuth2ClientProperties;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createClients() {
        if (ObjectUtils.isEmpty(appOAuth2ClientProperties.getClients())) {
            return;
        }
        appOAuth2ClientProperties.getClients()
                .forEach(this::createClient);
    }

    public void createClient(OAuth2Client client) {
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        RegisteredClient existing = registeredClientRepository.findByClientId(client.getId());
        if (Objects.nonNull(existing)) {
            return;
        }

        String encodedSecret = client.getSecretEncoded();
        if (ObjectUtils.isEmpty(encodedSecret)) {
            encodedSecret = passwordEncoder.encode(client.getRawSecret());
        }

        RegisteredClient.Builder registeredClientBuilder = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(client.getId())
                .clientSecret(encodedSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(
                        TokenSettings.builder()
                                .refreshTokenTimeToLive(client.getRefreshTokenTimeToLive())
                                .accessTokenTimeToLive(client.getAccessTokenTimeToLive())
                                .build()
                )
                .clientSettings(
                        ClientSettings.builder()
                                .requireAuthorizationConsent(client.isRequireAuthorizationConsent())
                                .build()
                );
        client.getRedirectUris()
                        .forEach(registeredClientBuilder::redirectUri);
        RegisteredClient registeredClient = registeredClientBuilder.build();

        registeredClientRepository.save(registeredClient);
    }
}
