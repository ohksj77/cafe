package com.kimseungjin.cafe.domain.product.repository;

import com.kimseungjin.cafe.domain.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<Product, UUID>, ProductRepository {

    @Query(value = "SELECT * FROM product p WHERE p.owner_id = :ownerId AND MATCH(p.name) AGAINST(:name IN BOOLEAN MODE)", nativeQuery = true)
    List<Product> findByOwnerIdAndNameContaining(@Param("ownerId") final UUID ownerId, @Param("name") final String name);
}
