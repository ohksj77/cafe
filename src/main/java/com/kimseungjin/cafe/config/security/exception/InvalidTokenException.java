package com.kimseungjin.cafe.config.security.exception;

public class InvalidTokenException extends IllegalStateException {

    private static final String MESSAGE = "토큰이 유효하지 않습니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }
}
