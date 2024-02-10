package com.kimseungjin.cafe.domain.member.exception;

public class PhoneNumberAlreadyExistsException extends IllegalArgumentException {

    private static final String MESSAGE = "이미 존재하는 전화번호입니다.";

    public PhoneNumberAlreadyExistsException() {
        super(MESSAGE);
    }
}
