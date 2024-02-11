package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

public interface ProductRepository {
    void softDelete(final Product product);
    <S extends Product> S save(final S product);
}
