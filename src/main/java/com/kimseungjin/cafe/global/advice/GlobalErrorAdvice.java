package com.kimseungjin.cafe.global.advice;

import com.kimseungjin.cafe.config.security.exception.InvalidTokenException;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;

import jakarta.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public BaseResponse<Void> entityNotFound(final EntityNotFoundException e) {
        return BaseResponse.errorOf(HttpStatus.BAD_REQUEST, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public BaseResponse<Void> validationException(final ValidationException e) {
        return BaseResponse.errorOf(HttpStatus.BAD_REQUEST, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public BaseResponse<Void> invalidTokenException(final InvalidTokenException e) {
        return BaseResponse.errorOf(HttpStatus.BAD_REQUEST, e);
    }
}
