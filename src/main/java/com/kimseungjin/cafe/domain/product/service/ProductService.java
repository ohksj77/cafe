package com.kimseungjin.cafe.domain.product.service;

import com.kimseungjin.cafe.domain.member.service.AuthService;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.domain.product.mapper.ProductMapper;
import com.kimseungjin.cafe.domain.product.repository.ProductRepository;
import com.kimseungjin.cafe.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;

    public IdResponse<UUID> createProduct(final ProductRequest productRequest) {
        final UUID ownerId = authService.getLoginUserId();
        final Product product = productMapper.toEntity(productRequest, ownerId);
        final UUID id = productRepository.save(product).getId();

        return new IdResponse<>(id);
    }
}
