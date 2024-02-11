package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.global.audit.SoftDeleteRepository;

import java.util.UUID;

public interface JpaProductRepository extends SoftDeleteRepository<Product, UUID>, ProductRepository {}
