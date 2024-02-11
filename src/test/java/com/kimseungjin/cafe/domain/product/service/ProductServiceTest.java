package com.kimseungjin.cafe.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.exception.OwnerMismatchException;
import com.kimseungjin.cafe.domain.product.repository.ProductRepository;
import com.kimseungjin.cafe.fixture.product.ProductRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.global.exception.EntityNotFoundException;
import com.kimseungjin.cafe.support.service.LoginTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

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
}
