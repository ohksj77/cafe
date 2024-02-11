package com.kimseungjin.cafe.fixture.product;

import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public enum ProductResponseFixture {

    APPLE_JUICE(UUID.randomUUID(), "사과주스", 3000, "음료"),
    ORANGE_JUICE(UUID.randomUUID(), "오렌지주스", 3000, "음료"),
    AMERICANO(UUID.randomUUID(), "아메리카노", 3500, "커피");

    private final UUID id;
    private final String name;
    private final Integer price;
    private final String category;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .id(id)
                .name(name)
                .price(price)
                .category(category)
                .build();
    }

    public static ProductPageResponse toProductPageResponse() {
        return new ProductPageResponse(List.of(APPLE_JUICE.toResponse(), ORANGE_JUICE.toResponse(), AMERICANO.toResponse()), true);
    }
}
