package com.kimseungjin.cafe.domain.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.domain.member.service.MemberService;
import com.kimseungjin.cafe.fixture.member.SignupRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.support.controller.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

@WebMvcTest(
        controllers = MemberController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MemberControllerTest extends ControllerTest {

    @MockBean private MemberService memberService;

    @DisplayName("signup 메소드는")
    @Nested
    class Signup {

        private final IdResponse<UUID> idResponse = new IdResponse<>(UUID.randomUUID());

        @BeforeEach
        void setup() {
            when(memberService.signup(any(SignupRequest.class))).thenReturn(idResponse);
        }

        @DisplayName("정상적인 요청이 들어오면")
        @Nested
        class WhenRequestIsValid {

            @DisplayName("회원이 저장되고 pk가 반환된다")
            @Test
            void saveMember() throws Exception {
                final ResultActions perform = mockMvc.perform(
                        post("/members/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(SignupRequestFixture.SUCCESS1.toRequest())));

                perform.andExpect(status().isCreated())
                        .andExpect(jsonPath("$.data.id").value(idResponse.getId().toString()));
            }
        }

        @DisplayName("잘못된 전화번호로 요청시")
        @Nested
        class WhenPhoneNumberIsInvalid {

            @DisplayName("400 에러가 발생한다")
            @Test
            void signupValidationException() throws Exception {
                final ResultActions perform = mockMvc.perform(
                        post("/members/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(SignupRequestFixture.PHONE_NUMBER_FAILURE1.toRequest())));

                perform.andExpect(status().isBadRequest());
            }
        }

        @DisplayName("비밀번호가 비어있는 경우")
        @Nested
        class WhenPasswordIsEmpty {

            @DisplayName("400 에러가 발생한다")
            @Test
            void signupValidationException() throws Exception {
                final ResultActions perform = mockMvc.perform(
                        post("/members/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toRequestBody(SignupRequestFixture.PASSWORD_FAILURE.toRequest())));

                perform.andExpect(status().isBadRequest());
            }
        }
    }
}
