package com.formlaez.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
