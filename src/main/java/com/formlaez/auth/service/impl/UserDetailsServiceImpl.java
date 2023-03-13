package com.formlaez.auth.service.impl;

import com.formlaez.auth.common.enumeration.UserStatus;
import com.formlaez.auth.entity.JpaUser;
import com.formlaez.auth.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsernameAndStatus(username, UserStatus.Active)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        return toUserDetails(user, AuthorityUtils.NO_AUTHORITIES);
    }

    private UserDetails toUserDetails(JpaUser user, List<GrantedAuthority> authorities) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getStatus() == UserStatus.Active,
                true,
                true,
                user.getStatus() != UserStatus.Locked,
                authorities
        );
    }
}
