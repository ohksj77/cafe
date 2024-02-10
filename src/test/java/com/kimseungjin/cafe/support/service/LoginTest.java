package com.kimseungjin.cafe.support.service;


import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.support.database.ResetDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
public abstract class LoginTest extends ResetDatabase {

    @Autowired protected MemberRepository memberRepository;
    protected Member loginUser;

    @BeforeEach
    void setup() {
        resetDatabase();
        final Member member = new Member("010-0000-0000", "password");
        loginUser = memberRepository.save(member);
    }
}
