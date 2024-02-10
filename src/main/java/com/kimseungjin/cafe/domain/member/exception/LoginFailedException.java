package com.kimseungjin.cafe.domain.member.exception;

public class LoginFailedException extends IllegalArgumentException {

    private static final String MESSAGE = "로그인에 실패했습니다.";

    public LoginFailedException() {
        super(MESSAGE);
    }
}
