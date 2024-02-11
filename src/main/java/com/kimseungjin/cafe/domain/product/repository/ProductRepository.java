package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    <S extends Product> S save(final S product);

    Optional<Product> findById(final UUID id);

    void removeById(final UUID id);

    List<Product> findAllByOwnerId(final UUID ownerId, final Pageable pageable);
}
