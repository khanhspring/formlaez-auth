package com.formlaez.auth.configuration.property;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = AppOAuth2ClientProperties.PREFIX)
public class AppOAuth2ClientProperties {
    public static final String PREFIX = "formlaez.oauth2";

    private List<@Valid OAuth2Client> clients;

    @NotBlank
    private String defaultClient;
    private String issuer;
}
