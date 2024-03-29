package com.kimseungjin.cafe.domain.member.service;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.domain.member.dto.CredentialRequest;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.exception.LoginFailedException;
import com.kimseungjin.cafe.domain.member.exception.PhoneNumberAlreadyExistsException;
import com.kimseungjin.cafe.domain.member.mapper.MemberMapper;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.utils.BlackListUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final AuthService authService;
    private final BlackListUtils blackListUtils;

    @Transactional
    public IdResponse<UUID> signup(final CredentialRequest credentialRequest) {
        validatePhoneNumberExists(credentialRequest);
        final Member member = memberRepository.save(memberMapper.toEntity(credentialRequest));

        return new IdResponse<>(member.getId());
    }

    private void validatePhoneNumberExists(final CredentialRequest credentialRequest) {
        if (isPhoneNumberExists(credentialRequest.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException();
        }
    }

    private boolean isPhoneNumberExists(final String phoneNumber) {
        return memberRepository.existsByLoginCredentialsPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public JwtToken login(final CredentialRequest credentialRequest) {
        final Member member = getMemberByPhoneNumber(credentialRequest);
        member.validatePassword(credentialRequest.getPassword());

        return authService.createJwtToken(member);
    }

    private Member getMemberByPhoneNumber(final CredentialRequest credentialRequest) {
        return memberRepository
                .findByLoginCredentialsPhoneNumber(credentialRequest.getPhoneNumber())
                .orElseThrow(LoginFailedException::new);
    }

    public void logout(final String bearerToken) {
        blackListUtils.add(bearerToken);
    }
}
