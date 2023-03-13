package com.formlaez.auth.client;

import com.formlaez.auth.client.model.CreateUserRequest;
import com.formlaez.auth.model.response.ResponseId;

import java.util.UUID;

public interface FormlaezServicesClient {
    ResponseId<UUID> createUser(CreateUserRequest request);
}
