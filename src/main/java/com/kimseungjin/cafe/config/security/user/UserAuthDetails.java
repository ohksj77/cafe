package com.kimseungjin.cafe.config.security.user;

import com.kimseungjin.cafe.domain.member.entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserAuthDetails implements UserDetails {

    private final Member member;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public UserAuthDetails(
            final Member member, final Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.name = member.getLoginCredentials().getPhoneNumber();
        this.authorities = authorities;
        this.password = member.getLoginCredentials().getPassword();
        this.username = member.getId().toString();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = member.isActivated();
    }
}
