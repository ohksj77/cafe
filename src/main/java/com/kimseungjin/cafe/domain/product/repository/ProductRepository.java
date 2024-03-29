package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    <S extends Product> S save(final S product);

    Optional<Product> findById(final UUID id);

    void removeById(final UUID id);

    Page<Product> findAllByOwnerId(final UUID ownerId, final Pageable pageable);

    List<Product> findAllByOwnerIdAndChosungContaining(final UUID ownerId, final String chosung);

    List<Product> findByOwnerIdAndNameContaining(final UUID ownerId, final String name);

    List<Product> findByOwnerIdAndName(final UUID ownerId, final String name);
}
