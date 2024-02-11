package com.kimseungjin.cafe.domain.product.controller;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.service.ProductService;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<IdResponse<UUID>> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        return BaseResponse.successOf(HttpStatus.CREATED, productService.createProduct(productRequest));
    }
}
