package com.kimseungjin.cafe.domain.member.exception;

public class MemberAlreadyExistsException extends IllegalArgumentException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public MemberAlreadyExistsException() {
        super(MESSAGE);
    }
}
