package com.formlaez.auth.client.model;

import com.formlaez.auth.common.enumeration.UserStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus status;
}
