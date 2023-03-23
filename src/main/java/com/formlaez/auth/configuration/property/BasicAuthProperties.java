package com.formlaez.auth.configuration.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
@ConfigurationProperties(prefix = BasicAuthProperties.PREFIX)
public class BasicAuthProperties {
    public static final String PREFIX = "formlaez.basic-auth";

    private List<BasicAuthUser> users;

    @Getter
    @Setter
    @Validated
    public static class BasicAuthUser {

        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotEmpty
        private List<String> roles;
    }
}
