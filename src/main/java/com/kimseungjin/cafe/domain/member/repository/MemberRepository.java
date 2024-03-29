package com.kimseungjin.cafe.domain.member.repository;

import com.kimseungjin.cafe.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Optional<Member> findById(final UUID id);

    <S extends Member> S save(final S member);

    boolean existsByLoginCredentialsPhoneNumber(final String phoneNumber);

    Optional<Member> findByLoginCredentialsPhoneNumber(final String phoneNumber);
}
