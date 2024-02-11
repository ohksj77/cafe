package com.kimseungjin.cafe.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    @Schema(
            description = "상품 ID",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(
            description = "상품명",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "아메리카노")
    private String name;

    @Schema(
            description = "가격",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "3500")
    private Integer price;

    @Schema(
            description = "카테고리",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "커피")
    private String category;
}
