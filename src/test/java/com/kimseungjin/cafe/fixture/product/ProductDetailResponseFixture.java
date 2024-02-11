package com.kimseungjin.cafe.fixture.product;

import com.kimseungjin.cafe.domain.product.dto.ProductDetailResponse;
import com.kimseungjin.cafe.domain.product.entity.ProduceSize;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public enum ProductDetailResponseFixture {
    RESPONSE1(
            UUID.randomUUID(),
            "음료",
            5000,
            3000,
            "초코라떼",
            "진한 초코가 들어간 라떼",
            "123456781",
            LocalDate.now().plusDays(7),
            ProduceSize.SMALL),
    RESPONSE2(
            UUID.randomUUID(),
            "음료",
            5000,
            3000,
            "카페라떼",
            "시그니처 라떼",
            "123456782",
            LocalDate.now().plusWeeks(1),
            ProduceSize.SMALL);

    private final UUID ownerId;
    private final String category;
    private final Integer price;
    private final Integer cost;
    private final String name;
    private final String description;
    private final String barcode;
    private final LocalDate expirationDate;
    private final ProduceSize productSize;

    public ProductDetailResponse toResponse() {
        return ProductDetailResponse.builder()
                .ownerId(ownerId)
                .category(category)
                .price(price)
                .cost(cost)
                .name(name)
                .description(description)
                .barcode(barcode)
                .expirationDate(expirationDate)
                .productSize(productSize)
                .build();
    }
}
