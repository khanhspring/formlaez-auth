package com.formlaez.auth.configuration.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

/**
 * @author khanhspring
 */
@Data
@Validated
public class OAuth2Client {
    public static final String PREFIX = "formlaez.oauth2.client";

    @NotBlank
    private String id;
    private String rawSecret;
    private String secretEncoded;
    @NotNull
    private Integer refreshTokenTimeToLiveInSeconds;
    @NotNull
    private Integer accessTokenTimeToLiveInSeconds;
    @NotEmpty
    private List<String> redirectUris;
    private boolean requireAuthorizationConsent;

    public Duration getRefreshTokenTimeToLive() {
        return Duration.ofSeconds(refreshTokenTimeToLiveInSeconds);
    }
    public Duration getAccessTokenTimeToLive() {
        return Duration.ofSeconds(accessTokenTimeToLiveInSeconds);
    }
}
