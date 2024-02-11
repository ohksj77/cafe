package com.kimseungjin.cafe.domain.product.controller.advice;

import com.kimseungjin.cafe.domain.product.exception.OwnerMismatchException;
import com.kimseungjin.cafe.global.dto.BaseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OwnerMismatchException.class)
    public BaseResponse<Void> ownerMismatch(final OwnerMismatchException e) {
        return BaseResponse.errorOf(HttpStatus.FORBIDDEN, e);
    }
}
