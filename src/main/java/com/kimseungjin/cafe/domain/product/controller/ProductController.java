package com.kimseungjin.cafe.domain.product.controller;

import com.kimseungjin.cafe.domain.product.dto.ProductDetailResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.domain.product.service.ProductService;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<IdResponse<UUID>> registerProduct(
            @RequestBody @Valid final ProductRequest productRequest) {
        return BaseResponse.successOf(
                HttpStatus.CREATED, productService.registerProduct(productRequest));
    }

    @Override
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(
            @PathVariable("id") final UUID id,
            @RequestBody @Valid final ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
    }

    @Override
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("id") final UUID id) {
        productService.removeProduct(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProductPageResponse> getProducts(
            @RequestParam(name = "page", defaultValue = "0") final Integer page) {
        return BaseResponse.successOf(HttpStatus.OK, productService.getProducts(page));
    }

    @Override
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ProductDetailResponse> getProductDetail(@PathVariable("id") final UUID id) {
        return BaseResponse.successOf(HttpStatus.OK, productService.getProductDetail(id));
    }

    @Override
    @GetMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<ProductResponse>> searchProducts(@RequestParam("q") final String query) {
        return BaseResponse.successOf(HttpStatus.OK, productService.searchProducts(query));
    }
}
