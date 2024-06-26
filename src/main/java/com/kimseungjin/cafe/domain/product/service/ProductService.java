package com.kimseungjin.cafe.domain.product.service;

import com.kimseungjin.cafe.domain.member.service.AuthService;
import com.kimseungjin.cafe.domain.product.dto.ProductDetailResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.domain.product.mapper.ProductMapper;
import com.kimseungjin.cafe.domain.product.repository.ProductRepository;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;
import com.kimseungjin.cafe.utils.ChosungParseUtils;
import com.kimseungjin.cafe.utils.HangulUtils;
import com.kimseungjin.cafe.utils.PageableUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        productRepository.removeById(id);
    }

    @Transactional(readOnly = true)
    public ProductPageResponse getProducts(final Integer page) {
        final UUID ownerId = authService.getLoginUserId();
        final Page<Product> products =
                productRepository.findAllByOwnerId(ownerId, PageableUtils.pageableFrom(page));

        return productMapper.toProductPageResponse(products);
    }

    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetail(final UUID id) {
        final Product product = getEntity(id);
        product.validateOwner(authService.getLoginUserId());

        return productMapper.toProductDetailResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(final String query) {
        final UUID ownerId = authService.getLoginUserId();

        final List<Product> products = getProducts(ownerId, query);
        return productMapper.toResponses(products);
    }

    private List<Product> getProducts(final UUID ownerId, final String query) {
        if (HangulUtils.isHangul(query)) {
            return productRepository
                    .findByOwnerIdAndNameContaining(ownerId, ChosungParseUtils.parse(query))
                    .stream()
                    .filter(product -> product.getName().contains(query))
                    .toList();
        }
        return checkChosungAndGetProducts(ownerId, query);
    }

    private List<Product> checkChosungAndGetProducts(final UUID ownerId, final String query) {
        if (HangulUtils.isChosung(query)) {
            return productRepository.findAllByOwnerIdAndChosungContaining(ownerId, query);
        }
        return productRepository.findByOwnerIdAndName(ownerId, query);
    }
}
