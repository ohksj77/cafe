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

import java.util.List;
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

    @DisplayName("findAllByOwnerIdAndChosungContaining 메소드는")
    @Nested
    class FindAllByOwnerIdAndChosungContaining {

        private final UUID ownerId = UUID.randomUUID();
        private final List<Product> products =
                List.of(
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CAFE_LATTE.toEntity(ownerId));

        @BeforeEach
        void setup() {
            products.forEach(productRepository::save);
        }

        @DisplayName("초성 일부를 검색하면")
        @Nested
        class WhenProductExists {

            @DisplayName("해당하는 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<Product> result =
                        productRepository.findAllByOwnerIdAndChosungContaining(ownerId, "ㅊㅋ");

                assertThat(result).hasSize(2);
            }
        }

        @DisplayName("초성의 공통 부분을 검색하면")
        @Nested
        class WhenProductExistsWithCommonPart {

            @DisplayName("해당하는 모든 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<Product> result =
                        productRepository.findAllByOwnerIdAndChosungContaining(ownerId, "ㄹㄸ");

                assertThat(result).hasSize(3);
            }
        }
    }

    @DisplayName("findAllByOwnerIdAndNameContaining 메소드는")
    @Nested
    class FindAllByOwnerIdAndNameContaining {

        private final UUID ownerId = UUID.randomUUID();
        private final List<Product> products =
                List.of(
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CAFE_LATTE.toEntity(ownerId));

        @BeforeEach
        void setup() {
            products.forEach(productRepository::save);
        }

        @DisplayName("상품 이름 일부를 검색하면")
        @Nested
        class WhenProductExists {

            @DisplayName("해당하는 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<Product> result =
                        productRepository.findByOwnerIdAndNameContaining(ownerId, "초코");

                assertThat(result).hasSize(2);
            }
        }

        @DisplayName("상품 이름의 공통 부분을 검색하면")
        @Nested
        class WhenProductExistsWithCommonPart {

            @DisplayName("해당하는 모든 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<Product> result =
                        productRepository.findByOwnerIdAndNameContaining(ownerId, "라떼");

                assertThat(result).hasSize(3);
            }
        }
    }

    @DisplayName("findAllByOwnerIdAndName 메소드는")
    @Nested
    class FindAllByOwnerIdAndName {

        private final UUID ownerId = UUID.randomUUID();
        private final List<Product> products =
                List.of(
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CHOCO_LATTE.toEntity(ownerId),
                        ProductEntityFixture.CAFE_LATTE.toEntity(ownerId));

        @BeforeEach
        void setup() {
            products.forEach(productRepository::save);
        }

        @DisplayName("상품 이름을 검색하면")
        @Nested
        class WhenProductExists {

            @DisplayName("해당하는 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<Product> result =
                        productRepository.findByOwnerIdAndName(ownerId, "초코라떼");

                assertThat(result).hasSize(2);
            }
        }

        @DisplayName("상품 이름 일부를 검색하면")
        @Nested
        class WhenProductExistsWithPart {

            @DisplayName("해당하는 상품을 반환하지 않는다")
            @Test
            void itReturnsProductList() {
                final List<Product> result = productRepository.findByOwnerIdAndName(ownerId, "초코");

                assertThat(result).isEmpty();
            }
        }
    }
}
