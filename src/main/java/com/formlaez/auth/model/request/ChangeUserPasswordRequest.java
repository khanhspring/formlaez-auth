package com.formlaez.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserPasswordRequest {
    private UUID userId;
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
