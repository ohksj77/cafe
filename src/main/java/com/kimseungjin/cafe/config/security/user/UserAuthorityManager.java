package com.kimseungjin.cafe.config.security.user;

import com.kimseungjin.cafe.domain.member.entity.Role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserAuthorityManager {

    private final Map<Role, SimpleGrantedAuthority> authorities;

    public UserAuthorityManager() {
        authorities = new EnumMap<>(Role.class);
        authorities.put(Role.ROLE_ADMIN, new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
        authorities.put(Role.ROLE_USER, new SimpleGrantedAuthority(Role.ROLE_USER.name()));
    }

    public List<SimpleGrantedAuthority> mapToAuthorities(final Role role) {
        return List.of(getAuthorityByRole(role));
    }

    private SimpleGrantedAuthority getAuthorityByRole(final Role role) {
        return authorities.get(role);
    }
}
