package com.kimseungjin.cafe.domain.product.service;

import com.kimseungjin.cafe.domain.member.service.AuthService;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.domain.product.mapper.ProductMapper;
import com.kimseungjin.cafe.domain.product.repository.ProductRepository;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;

    @Transactional
    public IdResponse<UUID> registerProduct(final ProductRequest productRequest) {
        final UUID ownerId = authService.getLoginUserId();
        final Product product = productMapper.toEntity(productRequest, ownerId);
        final UUID id = productRepository.save(product).getId();

        return new IdResponse<>(id);
    }

    @Transactional
    public void updateProduct(final UUID id, final ProductRequest productRequest) {
        final Product product = getEntity(id);
        product.validateOwner(authService.getLoginUserId());

        product.update(
                productRequest.getCategory(),
                productRequest.getPrice(),
                productRequest.getCost(),
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getBarcode(),
                productRequest.getExpirationDate(),
                productRequest.getProductSize());
    }

    private Product getEntity(final UUID id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void removeProduct(final UUID id) {
        final Product product = getEntity(id);
        product.validateOwner(authService.getLoginUserId());

        productRepository.remove(product);
    }
}
