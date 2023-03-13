package com.formlaez.auth.configuration;

import com.formlaez.auth.client.FormlaezServicesClient;
import com.formlaez.auth.configuration.security.FederatedIdentityConfigurer;
import com.formlaez.auth.configuration.security.UserRepositoryOAuth2UserHandler;
import com.formlaez.auth.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          CustomLogoutSuccessHandler logoutSuccessHandler) throws Exception {
        FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer()
                .oauth2UserHandler(userRepositoryOAuth2UserHandler);
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/assets/**", "/css/**", "/images/**", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .and()
                .apply(federatedIdentityConfigurer)
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
