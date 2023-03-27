package com.formlaez.auth.service.internal;

import com.formlaez.auth.model.request.ChangeUserPasswordRequest;
import com.formlaez.auth.model.request.CreateUserRequest;

import java.util.UUID;

public interface UserInternalService {
    String create(CreateUserRequest request);
    void changePassword(ChangeUserPasswordRequest request);
}
