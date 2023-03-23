package com.formlaez.auth.configuration;

import com.formlaez.auth.configuration.security.FederatedIdentityConfigurer;
import com.formlaez.auth.configuration.security.UserRepositoryOAuth2UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Bean
    @Order(1)
    SecurityFilterChain internalSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/internal/**")
                .authorizeHttpRequests()
                .requestMatchers("/internal/ping").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .userDetailsService(inMemoryUserDetailsManager)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          CustomLogoutSuccessHandler logoutSuccessHandler) throws Exception {
        var identityConfigurer = new FederatedIdentityConfigurer().oauth2UserHandler(userRepositoryOAuth2UserHandler);
        http
                .securityMatcher("/**")
                .authorizeHttpRequests()
                .requestMatchers("/assets/**", "/css/**", "/images/**", "/login", "/api/internal/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .and()
                .apply(identityConfigurer)
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
