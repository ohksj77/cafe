package com.kimseungjin.cafe.domain.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.fixture.product.ProductEntityFixture;
import com.kimseungjin.cafe.support.repository.RepositoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.UUID;

class ProductRepositoryTest extends RepositoryTest {

    @Autowired private ProductRepository productRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    @DisplayName("findById 메소드는")
    @Nested
    class FindById {

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            private UUID id;

            @BeforeEach
            void setup() {
                this.id = productRepository.save(ProductEntityFixture.CHOCO_LATTE.toEntity()).getId();
            }

            @DisplayName("상품을 반환한다")
            @Test
            void itReturnsProduct() {
                final Optional<Product> result = productRepository.findById(id);
                assertThat(result).isPresent();
            }
        }

        @DisplayName("상품이 존재하지 않으면")
        @Nested
        class WhenProductNotExists {

            @DisplayName("빈 Optional을 반환한다")
            @Test
            void itReturnsEmptyOptional() {
                final Optional<Product> result = productRepository.findById(UUID.randomUUID());
                assertThat(result).isEmpty();
            }
        }
    }
}
