package com.kimseungjin.cafe.domain.member.controller.advice;

import com.kimseungjin.cafe.domain.member.exception.PhoneNumberAlreadyExistsException;
import com.kimseungjin.cafe.global.dto.BaseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public BaseResponse<Void> memberAlreadyExists(final PhoneNumberAlreadyExistsException e) {
        return BaseResponse.errorOf(HttpStatus.CONFLICT, e);
    }
}
