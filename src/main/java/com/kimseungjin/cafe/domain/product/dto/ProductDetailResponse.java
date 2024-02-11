package com.kimseungjin.cafe.domain.product.dto;

import com.kimseungjin.cafe.domain.product.entity.ProduceSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {
    private UUID id;
    private UUID ownerId;
    private String category;
    private Integer price;
    private Integer cost;
    private String name;
    private String description;
    private String barcode;
    private LocalDate expirationDate;
    private ProduceSize productSize;
}
