package com.formlaez.auth.api.internal;

import com.formlaez.auth.model.request.CreateUserRequest;
import com.formlaez.auth.model.response.ResponseId;
import com.formlaez.auth.service.internal.UserInternalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "internal/users")
public class UserInternalController {

    private final UserInternalService userInternalService;

    @PostMapping
    public ResponseId<UUID> create(@RequestBody @Valid CreateUserRequest request) {
        var id = userInternalService.create(request);
        return ResponseId.of(UUID.fromString(id));
    }
}
