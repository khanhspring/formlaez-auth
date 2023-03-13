package com.formlaez.auth.configuration.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author khanhspring
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = FormlaezServicesClientProperties.PREFIX)
public class FormlaezServicesClientProperties {
    public static final String PREFIX = "formlaez.client.formlaez-services";

    @NotBlank
    private String baseUrl;
    @NotBlank
    private String authorization;
}
