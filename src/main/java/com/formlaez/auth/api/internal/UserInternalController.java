package com.formlaez.auth.api.internal;

import com.formlaez.auth.model.request.ChangeUserPasswordRequest;
import com.formlaez.auth.model.request.CreateUserRequest;
import com.formlaez.auth.model.response.ResponseId;
import com.formlaez.auth.service.internal.UserInternalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("{userId}/change-password")
    public void changePassword(@PathVariable UUID userId,
                               @RequestBody @Valid ChangeUserPasswordRequest request) {
        request.setUserId(userId);
        userInternalService.changePassword(request);
    }
}
