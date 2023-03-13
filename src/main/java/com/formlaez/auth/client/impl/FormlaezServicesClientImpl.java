package com.formlaez.auth.client.impl;

import com.formlaez.auth.client.FormlaezServicesClient;
import com.formlaez.auth.client.model.CreateUserRequest;
import com.formlaez.auth.common.exception.ApplicationException;
import com.formlaez.auth.configuration.property.FormlaezServicesClientProperties;
import com.formlaez.auth.model.response.ResponseId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FormlaezServicesClientImpl implements FormlaezServicesClient {

    private final WebClient webClient;

    public FormlaezServicesClientImpl(FormlaezServicesClientProperties properties) {
        webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .build();
    }

    @Override
    public ResponseId<UUID> createUser(CreateUserRequest request) {
        try {
            ResponseEntity<ResponseId<UUID>> response = webClient.post()
                    .uri("/users")
                    .body(Mono.just(request), CreateUserRequest.class)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<ResponseId<UUID>>() {})
                    .block();
            return Objects.requireNonNull(response).getBody();
        } catch (WebClientResponseException e) {
            log.error("Create user error, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            log.error("Create user error", e);
            throw new ApplicationException(e);
        }
    }
}
