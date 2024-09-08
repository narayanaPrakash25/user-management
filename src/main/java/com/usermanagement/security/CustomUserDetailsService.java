package com.usermanagement.security;

import com.usermanagement.entity.UserEntity;
import com.usermanagement.services.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = this.userService.findUserByUsername(username).orElseThrow(() -> new NullPointerException("User not exist"));
        return new UserPrincipal(user);
    }
}
