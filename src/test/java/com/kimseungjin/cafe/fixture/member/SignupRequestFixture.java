package com.kimseungjin.cafe.fixture.member;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SignupRequestFixture {
    SUCCESS1("010-0000-0000", "password"),
    SUCCESS2("010-0000-0001", "password"),
    PHONE_NUMBER_FAILURE1("010-0-0", "password"),
    PHONE_NUMBER_FAILURE2("010-0asd-0rew", "password"),
    PASSWORD_FAILURE("010-0000-0000", "");

    private final String phoneNumber;
    private final String password;

    public SignupRequest toRequest() {
        return new SignupRequest(this.phoneNumber, this.password);
    }
}
