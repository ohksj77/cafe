package com.kimseungjin.cafe.domain.product.dto;

import com.kimseungjin.cafe.domain.product.entity.ProduceSize;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @Schema(
            description = "카테고리",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "라떼")
    @NotBlank
    private String category;

    @Schema(
            description = "가격",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "5000")
    @NotNull
    private Integer price;

    @Schema(
            description = "원가",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "3000")
    @NotNull
    private Integer cost;

    @Schema(
            description = "이름",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "초코라떼")
    @NotBlank
    private String name;

    @Schema(
            description = "설명",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "진한 초코가 들어간 라떼")
    @NotBlank
    private String description;

    @Schema(
            description = "바코드",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1234567890")
    @NotBlank
    private String barcode;

    @Schema(
            description = "유통기한",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2024-02-12")
    @Future
    private LocalDate expirationDate;

    @Schema(
            description = "상품사이즈",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "LARGE")
    @NotNull
    private ProduceSize productSize;
}
