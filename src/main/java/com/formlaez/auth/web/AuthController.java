package com.formlaez.auth.web;

import com.formlaez.auth.configuration.property.AppOAuth2ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final RegisteredClientRepository clientRepo;
	private final AppOAuth2ClientProperties properties;

	@GetMapping("auth")
	public String prepareLogin(@RequestParam(name = "client_id", required = false) String clientId,
							   @RequestParam(name = "state", required = false) String state) {
		clientId = Objects.requireNonNullElse(clientId, properties.getDefaultClient());
		var client = clientRepo.findByClientId(clientId);
		Assert.notNull(client, "Client not found");
		var redirectUris = client.getRedirectUris();
		return "redirect:/oauth2/authorize?response_type=code&client_id="
				+ client.getClientId()
				+ "&redirect_uri=" + redirectUris.iterator().next()
				+ "&state=" + state;
	}

}
