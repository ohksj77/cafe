package com.kimseungjin.cafe.domain.member.service;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.exception.PhoneNumberAlreadyExistsException;
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

    public IdResponse<UUID> signup(final SignupRequest signupRequest) {
        validatePhoneNumberExists(signupRequest);
        final Member member = memberRepository.save(memberMapper.toEntity(signupRequest));

        return new IdResponse<>(member.getId());
    }

    private void validatePhoneNumberExists(final SignupRequest signupRequest) {
        if (isPhoneNumberExists(signupRequest.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException();
        }
    }

    private boolean isPhoneNumberExists(final String phoneNumber) {
        return memberRepository.existsByLoginInfoPhoneNumber(phoneNumber);
    }
}
