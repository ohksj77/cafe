package com.kimseungjin.cafe.fixture.member;

import com.kimseungjin.cafe.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberEntityFixture {
    MEMBER1("010-1234-5678", "1234"),
    MEMBER2("010-0000-5678", "0000"),
    MEMBER3("010-1234-0000", "12345");

    private final String phoneNumber;
    private final String password;

    public Member toEntity() {
        return Member.builder().phoneNumber(this.phoneNumber).password(this.password).build();
    }
}
