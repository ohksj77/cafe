package com.kimseungjin.cafe.support.service;

import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.fixture.member.MemberEntityFixture;
import com.kimseungjin.cafe.support.database.DatabaseSweepTest;
import com.kimseungjin.cafe.support.database.FlywayDisableTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
@DatabaseSweepTest
public abstract class LoginTest extends FlywayDisableTest {

    @Autowired protected MemberRepository memberRepository;
    protected Member loginUser;

    @BeforeEach
    void setup() {
        final Member member = MemberEntityFixture.MEMBER1.toEntity();
        loginUser = memberRepository.save(member);
    }
}
