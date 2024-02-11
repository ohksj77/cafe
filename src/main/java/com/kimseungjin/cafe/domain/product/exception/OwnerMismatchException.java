package com.kimseungjin.cafe.domain.product.exception;

public class OwnerMismatchException extends IllegalArgumentException {

    private static final String MESSAGE = "해당 상품의 소유자가 아닙니다.";

    public OwnerMismatchException() {
        super(MESSAGE);
    }
}
