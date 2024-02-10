package com.kimseungjin.cafe.domain.member.service;

import com.kimseungjin.cafe.domain.member.dto.SignInRequest;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.exception.MemberAlreadyExistsException;
import com.kimseungjin.cafe.domain.member.mapper.MemberMapper;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public IdResponse<UUID> signIn(final SignInRequest signInRequest) {
        validatePhoneNumber(signInRequest);
        final Member member = memberRepository.save(memberMapper.toEntity(signInRequest));

        return new IdResponse<>(member.getId());
    }

    private void validatePhoneNumber(final SignInRequest signinRequest) {
        if (isPhoneNumberExists(signinRequest.getPhoneNumber())) {
            throw new MemberAlreadyExistsException();
        }
    }

    private boolean isPhoneNumberExists(final String phoneNumber) {
        return memberRepository.existsByLoginInfoPhoneNumber(phoneNumber);
    }
}
