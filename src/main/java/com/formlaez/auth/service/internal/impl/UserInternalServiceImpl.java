package com.formlaez.auth.service.internal.impl;

import com.formlaez.auth.common.enumeration.UserStatus;
import com.formlaez.auth.common.exception.ApplicationException;
import com.formlaez.auth.entity.JpaUser;
import com.formlaez.auth.model.request.ChangeUserPasswordRequest;
import com.formlaez.auth.model.request.CreateUserRequest;
import com.formlaez.auth.repository.JpaUserRepository;
import com.formlaez.auth.service.internal.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.formlaez.auth.common.enumeration.UserStatus.Active;

@Service
@RequiredArgsConstructor
public class UserInternalServiceImpl implements UserInternalService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApplicationException("User with the email already existed");
        }
        var newUser = JpaUser.builder()
                .username(request.getEmail())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .status(Active)
                .build();
        return userRepository.save(newUser).getId();
    }

    @Override
    @Transactional
    public void changePassword(ChangeUserPasswordRequest request) {
        var user = userRepository.findById(request.getUserId().toString())
                .orElseThrow();

        if (Objects.isNull(user.getPassword()) && Objects.nonNull(request.getCurrentPassword())) {
            throw new ApplicationException("Current password is incorrect");
        }

        if (Objects.nonNull(user.getPassword())
                && !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ApplicationException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
