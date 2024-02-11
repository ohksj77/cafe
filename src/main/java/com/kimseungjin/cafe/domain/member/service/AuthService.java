package com.kimseungjin.cafe.domain.member.service;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.config.security.jwt.JwtTokenProvider;
import com.kimseungjin.cafe.config.security.user.UserAuthDetails;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public JwtToken createJwtToken(final Member member) {
        return jwtTokenProvider.createToken(member);
    }

    public UUID getLoginUserId() {
        return UUID.fromString(getUserAuthDetails().getUsername());
    }

    private UserAuthDetails getUserAuthDetails() {
        return (UserAuthDetails)
                (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public Member getLoginUser() {
        return memberRepository
                .findById(getLoginUserId())
                .orElseThrow(EntityNotFoundException::new);
    }
}
