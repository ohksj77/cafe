package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

public interface ProductRepository {
    <S extends Product> S save(final S product);
}
