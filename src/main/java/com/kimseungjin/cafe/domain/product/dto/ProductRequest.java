package com.kimseungjin.cafe.domain.product.dto;

import com.kimseungjin.cafe.domain.product.entity.ProduceSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String category;
    private Integer price;
    private Integer cost;
    private String name;
    private String description;
    private String barcode;
    private LocalDate expirationDate;
    private ProduceSize productSize;
}
