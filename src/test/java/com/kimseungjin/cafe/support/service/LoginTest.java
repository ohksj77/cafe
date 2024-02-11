package com.kimseungjin.cafe.support.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.domain.member.service.AuthService;
import com.kimseungjin.cafe.fixture.member.MemberEntityFixture;
import com.kimseungjin.cafe.support.database.DatabaseSweepTest;
import com.kimseungjin.cafe.support.database.FlywayDisableTest;
import com.kimseungjin.cafe.support.utils.TestBlackListUtils;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import(TestBlackListUtils.class)
@DatabaseTest
@DatabaseSweepTest
public abstract class LoginTest extends FlywayDisableTest {

    @Autowired protected MemberRepository memberRepository;
    @MockBean protected AuthService authService;
    protected Member loginUser;

    @BeforeEach
    void setup() {
        final Member member = MemberEntityFixture.MEMBER1.toEntity();
        loginUser = memberRepository.save(member);
        when(authService.getLoginUserId()).thenReturn(loginUser.getId());
        when(authService.getLoginUser()).thenReturn(loginUser);
        when(authService.createJwtToken(any(Member.class)))
                .thenReturn(new JwtToken("this-is.access.token", "this-is.refresh.token"));
    }
}
