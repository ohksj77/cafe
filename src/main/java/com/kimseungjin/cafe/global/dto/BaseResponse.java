package com.kimseungjin.cafe.global.dto;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {

    private MetaResponse meta;
    private T data;

    private BaseResponse(final Integer code, final String message, final T data) {
        this.meta = new MetaResponse(code, message);
        this.data = data;
    }

    public static <T> BaseResponse<T> successOf(final HttpStatus httpStatus, final T data) {
        return new BaseResponse<>(httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

    public static BaseResponse<Void> errorOf(final HttpStatus httpStatus, final Exception exception) {
        return new BaseResponse<>(httpStatus.value(), exception.getMessage(), null);
    }
}
