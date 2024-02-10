package com.kimseungjin.cafe.domain.member.controller.advice;

import com.kimseungjin.cafe.domain.member.exception.MemberAlreadyExistsException;
import com.kimseungjin.cafe.global.dto.BaseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberAlreadyExistsException.class)
    public BaseResponse<Void> memberAlreadyExists(final MemberAlreadyExistsException e) {
        return BaseResponse.errorOf(HttpStatus.BAD_REQUEST, e);
    }
}
