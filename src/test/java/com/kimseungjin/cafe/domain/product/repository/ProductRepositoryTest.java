package com.kimseungjin.cafe.domain.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.fixture.product.ProductEntityFixture;
import com.kimseungjin.cafe.support.repository.RepositoryTest;
import com.kimseungjin.cafe.utils.PageableUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

class ProductRepositoryTest extends RepositoryTest {

    @Autowired private ProductRepository productRepository;

    @DisplayName("findById 메소드는")
    @Nested
    class FindById {

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            private UUID id;

            @BeforeEach
            void setup() {
                this.id =
                        productRepository.save(ProductEntityFixture.CHOCO_LATTE.toEntity()).getId();
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

    @DisplayName("removeById 메소드는")
    @Nested
    class RemoveById {

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            private UUID id;

            @BeforeEach
            void setup() {
                this.id =
                        productRepository.save(ProductEntityFixture.CHOCO_LATTE.toEntity()).getId();
            }

            @DisplayName("상품을 삭제한다")
            @Test
            void itRemovesProduct() {
                productRepository.removeById(id);
                final Optional<Product> result = productRepository.findById(id);
                assertThat(result).isEmpty();
            }
        }

        @DisplayName("상품이 존재하지 않으면")
        @Nested
        class WhenProductNotExists {

            @DisplayName("아무것도 하지 않는다")
            @Test
            void itDoesNothing() {
                assertThatNoException()
                        .isThrownBy(() -> productRepository.removeById(UUID.randomUUID()));
            }
        }
    }

    @DisplayName("findAllByOwnerId 메소드는")
    @Nested
    class FindAllByOwnerId {

        private final UUID ownerId = UUID.randomUUID();

        @BeforeEach
        void setup() {
            Stream.generate(() -> ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId))
                    .limit(16)
                    .forEach(productRepository::save);
        }

        @DisplayName("상품이 다음 페이지에도 존재하는 페이지라면")
        @Nested
        class WhenProductExists {

            @DisplayName("상품 10개를 반환한다")
            @Test
            void itReturnsProductList() {
                final Page<Product> result =
                        productRepository.findAllByOwnerId(ownerId, PageableUtils.pageableFrom(0));
                assertThat(result.hasNext()).isTrue();
                assertThat(result.getContent()).hasSize(10);
            }
        }

        @DisplayName("마지막 페이지라면")
        @Nested
        class WhenLastPage {

            @DisplayName("상품 1개 이상 10개 이하를 반환한다")
            @Test
            void itReturnsProductList() {
                final Page<Product> result =
                        productRepository.findAllByOwnerId(ownerId, PageableUtils.pageableFrom(1));
                assertThat(result.hasNext()).isFalse();
                assertThat(result.getContent()).hasSizeBetween(1, 10);
            }
        }

        @DisplayName("상품이 없는 페이지라면")
        @Nested
        class WhenProductNotExistingPage {

            @DisplayName("상품 0개를 반환한다")
            @Test
            void itReturnsProductList() {
                final Page<Product> result =
                        productRepository.findAllByOwnerId(ownerId, PageableUtils.pageableFrom(2));
                assertThat(result).isEmpty();
            }
        }
    }
}
