package com.kimseungjin.cafe.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.fixture.member.MemberEntityFixture;
import com.kimseungjin.cafe.support.repository.RepositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberRepositoryTest extends RepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @DisplayName("ExsistsByLoginInfoPhoneNumber 메소드는")
    @Nested
    class ExistsByLoginInfoPhoneNumber {

        @DisplayName("전화번호가 존재하면")
        @Nested
        class WhenPhoneNumberExists {

            private final Member member = MemberEntityFixture.MEMBER1.toEntity();

            @BeforeEach
            void setup() {
                memberRepository.save(member);
            }

            @DisplayName("true를 반환한다")
            @Test
            void returnTrue() {
                final boolean isExists =
                        memberRepository.existsByLoginInfoPhoneNumber(
                                member.getLoginInfo().getPhoneNumber());
                assertThat(isExists).isTrue();
            }
        }

        @DisplayName("전화번호가 존재하지 않으면")
        @Nested
        class WhenPhoneNumberDoesNotExists {

            private final String phoneNumber = "010-1111-1111";

            @DisplayName("false를 반환한다")
            @Test
            void returnFalse() {
                final boolean isExists =
                        memberRepository.existsByLoginInfoPhoneNumber(phoneNumber);
                assertThat(isExists).isFalse();
            }
        }
    }
}
