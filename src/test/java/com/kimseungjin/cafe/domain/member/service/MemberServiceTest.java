package com.kimseungjin.cafe.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.domain.member.dto.CredentialRequest;
import com.kimseungjin.cafe.domain.member.exception.LoginFailedException;
import com.kimseungjin.cafe.domain.member.exception.PhoneNumberAlreadyExistsException;
import com.kimseungjin.cafe.domain.member.repository.MemberRepository;
import com.kimseungjin.cafe.fixture.member.CredentialRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.support.service.LoginTest;
import com.kimseungjin.cafe.utils.BlackListUtils;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class MemberServiceTest extends LoginTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BlackListUtils blackListUtils;

    @DisplayName("signup 메소드는")
    @Nested
    class Signup {

        @DisplayName("새로운 전화번호와 함께 요청이 들어오면")
        @Nested
        class WhenRequestIsValid {

            private final CredentialRequest credentialRequest =
                    CredentialRequestFixture.SUCCESS1.toRequest();

            @DisplayName("회원이 저장되고 pk가 반환된다")
            @Test
            void saveMember() {
                final IdResponse<UUID> idResponse = memberService.signup(credentialRequest);

                assertThat(idResponse.getId()).isNotNull();
                assertThat(
                                memberRepository.existsByLoginCredentialsPhoneNumber(
                                        credentialRequest.getPhoneNumber()))
                        .isTrue();
            }
        }

        @DisplayName("이미 존재하는 전화번호로 요청이 들어오면")
        @Nested
        class WhenPhoneNumberIsInvalid {

            private final CredentialRequest credentialRequest =
                    CredentialRequestFixture.SUCCESS2.toRequest();

            @BeforeEach
            void setup() {
                memberService.signup(credentialRequest);
            }

            @DisplayName("PhoneNumberAlreadyExistsException이 발생한다")
            @Test
            void throwPhoneNumberAlreadyExistsException() {
                assertThatThrownBy(() -> memberService.signup(credentialRequest))
                        .isInstanceOf(PhoneNumberAlreadyExistsException.class);
            }
        }
    }

    @DisplayName("login 메소드는")
    @Nested
    class Login {

        private final CredentialRequest credentialRequest =
                CredentialRequestFixture.SUCCESS1.toRequest();

        @BeforeEach
        void setup() {
            memberService.signup(credentialRequest);
        }

        @DisplayName("정상적인 요청이 들어오면")
        @Nested
        class WhenCredentialIsValid {

            @DisplayName("Jwt 토큰이 발급된다")
            @Test
            void createJwtToken() {
                final JwtToken jwtToken = memberService.login(credentialRequest);
                assertThat(jwtToken.getAccessToken()).isNotNull();
                assertThat(jwtToken.getRefreshToken()).isNotNull();
            }
        }

        @DisplayName("존재하지 않는 전화번호로 로그인 요청시")
        @Nested
        class WhenPhoneNumberNotExists {

            private final CredentialRequest notExistsPhoneNumber =
                    new CredentialRequest(
                            credentialRequest.getPhoneNumber() + "dummy",
                            credentialRequest.getPassword());

            @DisplayName("LoginFailedException이 발생한다")
            @Test
            void throwLoginFailedException() {
                assertThatThrownBy(() -> memberService.login(notExistsPhoneNumber))
                        .isInstanceOf(LoginFailedException.class);
            }
        }

        @DisplayName("비밀번호가 틀린 경우")
        @Nested
        class WhenPasswordIsInvalid {

            private final CredentialRequest wrongPassword =
                    new CredentialRequest(
                            credentialRequest.getPhoneNumber(),
                            credentialRequest.getPassword() + "dummy");

            @DisplayName("LoginFailedException이 발생한다")
            @Test
            void throwLoginFailedException() {
                assertThatThrownBy(() -> memberService.login(wrongPassword))
                        .isInstanceOf(LoginFailedException.class);
            }
        }
    }

    @DisplayName("logout 메소드는")
    @Nested
    class Logout {

        private final CredentialRequest credentialRequest =
                CredentialRequestFixture.SUCCESS1.toRequest();

        @BeforeEach
        void setup() {
            memberService.signup(credentialRequest);
        }

        @DisplayName("정상적인 요청이 들어오면")
        @Nested
        class WhenRequestIsValid {

            @DisplayName("Jwt 토큰이 블랙리스트에 추가된다")
            @Test
            void addTokenToBlackList() {
                final JwtToken jwtToken = memberService.login(credentialRequest);
                final String token = jwtToken.getAccessToken();
                memberService.logout(token);

                assertThat(blackListUtils.hasKey(token)).isTrue();
            }
        }
    }
}
