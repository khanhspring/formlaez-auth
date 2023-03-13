package com.formlaez.auth.configuration;

import com.formlaez.auth.configuration.property.AppOAuth2ClientProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RegisteredClientRepository clientRepo;
    private final AppOAuth2ClientProperties properties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        var clientId = Objects.requireNonNullElse(request.getParameter("client_id"), properties.getDefaultClient());
        var client = clientRepo.findByClientId(clientId);

        if (Objects.isNull(client)) {
            response.sendRedirect(request.getContextPath() + "/login?logout=true");
            return;
        }
        var redirectUris = client.getRedirectUris();
        var redirectUrl =  request.getContextPath() + "/oauth2/authorize?response_type=code&client_id="
                + client.getClientId()
                + "&redirect_uri="
                + redirectUris.iterator().next();
        response.sendRedirect(redirectUrl);
    }
}
