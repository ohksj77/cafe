package com.kimseungjin.cafe.fixture.product;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.entity.ProduceSize;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public enum ProductRequestFixture {

    SUCCESS_REQUEST1(
            "음료",
            5000,
            3000,
            "초코라떼",
            "진한 초코가 들어간 라떼",
            "123456789",
            LocalDate.now().plusDays(7),
            ProduceSize.SMALL
    ),
    FAILURE_REQUEST1(
            "음료",
            5000,
            3000,
            "초코라떼",
            "진한 초코가 들어간 라떼",
            "123456789",
            LocalDate.now().minusDays(1),
            ProduceSize.SMALL
    );

    private final String category;
    private final Integer price;
    private final Integer cost;
    private final String name;
    private final String description;
    private final String barcode;
    private final LocalDate expirationDate;
    private final ProduceSize productSize;

    public ProductRequest toRequest() {
        return new ProductRequest(category, price, cost, name, description, barcode, expirationDate, productSize);
    }
}
