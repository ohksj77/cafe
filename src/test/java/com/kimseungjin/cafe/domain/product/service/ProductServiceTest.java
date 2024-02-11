package com.kimseungjin.cafe.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.fixture.product.ProductRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.support.service.LoginTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class ProductServiceTest extends LoginTest {

    @Autowired private ProductService productService;

    @DisplayName("createProduct 메서드는")
    @Nested
    class CreateProduct {

        @DisplayName("ProductRequest가 정상적인 경우")
        @Nested
        class ContextWithValidProductRequest {

            private final ProductRequest productRequest =
                    ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

            @DisplayName("상품이 저장된다")
            @Test
            void createProduct() {
                final IdResponse<UUID> idResponse = productService.createProduct(productRequest);
                assertThat(idResponse.getId()).isNotNull();
            }
        }
    }
}
