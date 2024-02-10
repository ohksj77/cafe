package com.kimseungjin.cafe.config.security.user;

import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAuthDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final UserAuthorityManager userAuthorityManager;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return memberRepository
                .findById(UUID.fromString(username))
                .map(
                        member ->
                                new UserAuthDetails(
                                        member,
                                        userAuthorityManager.mapToAuthorities(member.getRole())))
                .orElseThrow(EntityNotFoundException::new);
    }
}
