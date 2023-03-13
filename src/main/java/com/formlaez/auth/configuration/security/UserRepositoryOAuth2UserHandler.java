/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.formlaez.auth.configuration.security;

import com.formlaez.auth.client.FormlaezServicesClient;
import com.formlaez.auth.client.model.CreateUserRequest;
import com.formlaez.auth.common.exception.ApplicationException;
import com.formlaez.auth.entity.JpaUser;
import com.formlaez.auth.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static com.formlaez.auth.common.enumeration.UserStatus.Active;
import static com.formlaez.auth.common.enumeration.UserStatus.Locked;

/**
 * Example {@link Consumer} to perform JIT provisioning of an {@link OAuth2User}.
 */

@Component
@RequiredArgsConstructor
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

	private final JpaUserRepository userRepository;
	private final FormlaezServicesClient formlaezServicesClient;

	private static final String GOOGLE_ISS = "accounts.google.com";
	private static final String EMAIL = "email";

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void accept(OAuth2User user) {
		URL iss = user.getAttribute(IdTokenClaimNames.ISS);
		if (Objects.nonNull(iss) && GOOGLE_ISS.equals(iss.getAuthority())) {
			handleGoogleUser(user);
			return;
		}
		throw new ApplicationException("Authentication provider is not supported");
	}

	private void handleGoogleUser(OAuth2User user) {
		String email = Objects.requireNonNull(user.getAttribute(EMAIL));
		var existedUser = this.userRepository.findByUsernameAndStatusIn(email, List.of(Active, Locked));
		if (existedUser.isPresent()) {
			return;
		}

		var newUser = JpaUser.builder()
				.username(email)
				.email(email)
				.firstName(user.getAttribute("given_name"))
				.lastName(user.getAttribute("family_name"))
				.status(Active)
				.build();
		newUser = userRepository.save(newUser);
		syncUserToService(newUser);
	}

	private void syncUserToService(JpaUser jpaUser) {
		var user = CreateUserRequest.builder()
				.id(UUID.fromString(jpaUser.getId()))
				.username(jpaUser.getUsername())
				.email(jpaUser.getEmail())
				.firstName(jpaUser.getFirstName())
				.lastName(jpaUser.getLastName())
				.status(jpaUser.getStatus())
				.build();
		formlaezServicesClient.createUser(user);
	}
}
