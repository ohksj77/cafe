package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<Product, UUID>, ProductRepository {}
