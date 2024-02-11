package com.kimseungjin.cafe.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponse {

    private List<ProductResponse> products;

    private Boolean hasNext;
}
