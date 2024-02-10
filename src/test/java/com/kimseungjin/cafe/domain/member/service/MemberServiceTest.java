package com.kimseungjin.cafe.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.domain.member.exception.PhoneNumberAlreadyExistsException;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.fixture.member.SignupRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.support.service.LoginTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class MemberServiceTest extends LoginTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @DisplayName("signup 메소드는")
    @Nested
    class Signup {

        @DisplayName("새로운 전화번호와 함께 요청이 들어오면")
        @Nested
        class WhenRequestIsValid {

            private final SignupRequest signupRequest = SignupRequestFixture.SUCCESS1.toRequest();

            @DisplayName("회원이 저장되고 pk가 반환된다")
            @Test
            void saveMember() {
                final IdResponse<UUID> idResponse = memberService.signup(signupRequest);

                assertThat(idResponse.getId()).isNotNull();
                assertThat(memberRepository.existsByLoginInfoPhoneNumber(signupRequest.getPhoneNumber())).isTrue();
            }
        }

        @DisplayName("이미 존재하는 전화번호로 요청이 들어오면")
        @Nested
        class WhenPhoneNumberIsInvalid {

            private final SignupRequest signupRequest = SignupRequestFixture.SUCCESS2.toRequest();

            @BeforeEach
            void setup() {
                memberService.signup(signupRequest);
            }


            @DisplayName("PhoneNumberAlreadyExistsException이 발생한다")
            @Test
            void throwPhoneNumberAlreadyExistsException() {
                assertThatThrownBy(() -> memberService.signup(signupRequest))
                        .isInstanceOf(PhoneNumberAlreadyExistsException.class);
            }
        }
    }
}
