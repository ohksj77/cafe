package com.kimseungjin.cafe.fixture.member;

import com.kimseungjin.cafe.domain.member.dto.CredentialRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CredentialRequestFixture {
    SUCCESS1("010-0000-0000", "password"),
    SUCCESS2("010-0000-0001", "password"),
    PHONE_NUMBER_FAILURE1("010-0-0", "password"),
    PHONE_NUMBER_FAILURE2("010-0asd-0rew", "password"),
    PASSWORD_FAILURE("010-0000-0000", "");

    private final String phoneNumber;
    private final String password;

    public CredentialRequest toRequest() {
        return new CredentialRequest(this.phoneNumber, this.password);
    }
}
