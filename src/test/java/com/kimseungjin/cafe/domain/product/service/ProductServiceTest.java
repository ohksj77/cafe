package com.kimseungjin.cafe.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.domain.product.exception.OwnerMismatchException;
import com.kimseungjin.cafe.domain.product.repository.ProductRepository;
import com.kimseungjin.cafe.fixture.product.ProductEntityFixture;
import com.kimseungjin.cafe.fixture.product.ProductRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;
import com.kimseungjin.cafe.support.service.LoginTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

class ProductServiceTest extends LoginTest {

    @Autowired private ProductService productService;
    @Autowired private ProductRepository productRepository;

    @DisplayName("createProduct 메서드는")
    @Nested
    class RegisterProduct {

        @DisplayName("ProductRequest가 정상적인 경우")
        @Nested
        class ContextWithValidProductRequest {

            private final ProductRequest productRequest =
                    ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

            @DisplayName("상품이 저장된다")
            @Test
            void createProduct() {
                final IdResponse<UUID> idResponse = productService.registerProduct(productRequest);
                assertThat(idResponse.getId()).isNotNull();
            }
        }
    }

    @DisplayName("updateProduct 메서드는")
    @Nested
    class UpdateProduct {

        private UUID beforeId;
        private final ProductRequest before = ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

        private final ProductRequest after = ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            @BeforeEach
            void setup() {
                final IdResponse<UUID> result = productService.registerProduct(before);
                beforeId = result.getId();
            }

            @DisplayName("상품이 수정된다")
            @Test
            void updateProduct() {
                productService.updateProduct(beforeId, after);
                assertThat(productRepository.findById(beforeId).orElseThrow().getName())
                        .isEqualTo(after.getName());
            }
        }

        @DisplayName("상품이 존재하지 않으면")
        @Nested
        class WhenProductNotExists {

            @DisplayName("EntityNotFoundException이 발생한다")
            @Test
            void itThrowsEntityNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(UUID.randomUUID(), after))
                        .isInstanceOf(EntityNotFoundException.class);
            }
        }

        @DisplayName("상품의 주인이 아니면")
        @Nested
        class WhenNotOwner {

            @BeforeEach
            void setup() {
                final IdResponse<UUID> result = productService.registerProduct(before);
                beforeId = result.getId();
            }

            @DisplayName("EntityNotFoundException이 발생한다")
            @Test
            void itThrowsEntityNotFoundException() {
                when(authService.getLoginUserId()).thenReturn(UUID.randomUUID());
                assertThatThrownBy(() -> productService.updateProduct(beforeId, after))
                        .isInstanceOf(OwnerMismatchException.class);
            }
        }
    }

    @DisplayName("removeProduct 메서드는")
    @Nested
    class RemoveProduct {

        private UUID productId;
        private final ProductRequest productRequest =
                ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            @BeforeEach
            void setup() {
                final IdResponse<UUID> result = productService.registerProduct(productRequest);
                productId = result.getId();
            }

            @DisplayName("상품이 삭제된다")
            @Test
            void removeProduct() {
                productService.removeProduct(productId);
                assertThat(productRepository.findById(productId)).isEmpty();
            }
        }

        @DisplayName("상품이 존재하지 않으면")
        @Nested
        class WhenProductNotExists {

            @DisplayName("EntityNotFoundException이 발생한다")
            @Test
            void itThrowsEntityNotFoundException() {
                assertThatThrownBy(() -> productService.removeProduct(UUID.randomUUID()))
                        .isInstanceOf(EntityNotFoundException.class);
            }
        }

        @DisplayName("상품의 주인이 아니면")
        @Nested
        class WhenNotOwner {

            @BeforeEach
            void setup() {
                final IdResponse<UUID> result = productService.registerProduct(productRequest);
                productId = result.getId();
            }

            @DisplayName("EntityNotFoundException이 발생한다")
            @Test
            void itThrowsEntityNotFoundException() {
                when(authService.getLoginUserId()).thenReturn(UUID.randomUUID());
                assertThatThrownBy(() -> productService.removeProduct(productId))
                        .isInstanceOf(OwnerMismatchException.class);
            }
        }
    }

    @DisplayName("getProducts 메서드는")
    @Nested
    class GetProducts {

        @BeforeEach
        void setup() {
            Stream.generate(ProductRequestFixture.SUCCESS_REQUEST1::toRequest)
                    .limit(13)
                    .forEach(productService::registerProduct);
        }

        @DisplayName("상품이 다음 페이지에도 존재하는 페이지면")
        @Nested
        class WhenProductExists {

            @DisplayName("상품을 10개 반환한다")
            @Test
            void itReturnsProducts() {
                final ProductPageResponse productPageResponse = productService.getProducts(0);
                assertThat(productPageResponse.getProducts()).isNotEmpty();
                assertThat(productPageResponse.getHasNext()).isTrue();
            }
        }

        @DisplayName("마지막 페이지면")
        @Nested
        class WhenLastPage {

            @DisplayName("상품을 1개 이상 10개 이하 반환한다")
            @Test
            void itReturnsProducts() {
                final ProductPageResponse productPageResponse = productService.getProducts(1);
                assertThat(productPageResponse.getProducts()).hasSizeBetween(1, 10);
                assertThat(productPageResponse.getHasNext()).isFalse();
            }
        }

        @DisplayName("상품이 존재하지 않은 페이지면")
        @Nested
        class WhenProductNotExistingPage {

            @DisplayName("빈 리스트를 반환한다")
            @Test
            void itReturnsEmptyList() {
                final ProductPageResponse productPageResponse = productService.getProducts(2);
                assertThat(productPageResponse.getProducts()).isNull();
            }
        }
    }

    @DisplayName("getProduct 메서드는")
    @Nested
    class GetProduct {

        @DisplayName("상품이 존재하면")
        @Nested
        class WhenProductExists {

            private UUID productId;

            @BeforeEach
            void setup() {
                final IdResponse<UUID> result =
                        productService.registerProduct(
                                ProductRequestFixture.SUCCESS_REQUEST1.toRequest());
                productId = result.getId();
            }

            @DisplayName("상품을 반환한다")
            @Test
            void itReturnsProduct() {
                assertThat(productService.getProductDetail(productId)).isNotNull();
            }
        }

        @DisplayName("상품이 존재하지 않으면")
        @Nested
        class WhenProductNotExists {

            @DisplayName("EntityNotFoundException이 발생한다")
            @Test
            void itThrowsEntityNotFoundException() {
                assertThatThrownBy(() -> productService.getProductDetail(UUID.randomUUID()))
                        .isInstanceOf(EntityNotFoundException.class);
            }
        }
    }

    @DisplayName("searchProducts 메서드는")
    @Nested
    class SearchProducts {

        @BeforeEach
        void setup() {
            productRepository.save(ProductEntityFixture.CAFE_LATTE.toEntity(loginUser.getId()));
        }

        @DisplayName("초성 일부를 검색하면")
        @Nested
        class WhenSearchWithChosung {

            private final String query = "ㅋㅍ";

            @DisplayName("해당하는 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<ProductResponse> productResponses = productService.searchProducts(query);
                assertThat(productResponses).isNotEmpty();
            }
        }

        @DisplayName("이름 일부를 검색하면")
        @Nested
        class WhenSearchWithName {

            private final String query = "카페";

            @DisplayName("해당하는 상품을 반환한다")
            @Test
            void itReturnsProductList() {
                final List<ProductResponse> productResponses = productService.searchProducts(query);
                assertThat(productResponses).isNotEmpty();
            }
        }

        @DisplayName("한글이 아닌 검색어의 경우")
        @Nested
        class WhenSearchWithEnglish {

            private final String query = "Latte";

            @DisplayName("영어로 검색한 결과(정확히 일치하는 경우)를 반환한다")
            @Test
            void itReturnsProductList() {
                final List<ProductResponse> productResponses = productService.searchProducts(query);
                assertThat(productResponses).isEmpty();
            }
        }
    }
}
