package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    <S extends Product> S save(final S product);

    Optional<Product> findById(final UUID id);
}
