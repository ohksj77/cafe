package com.kimseungjin.cafe.domain.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.service.ProductService;
import com.kimseungjin.cafe.fixture.product.ProductRequestFixture;
import com.kimseungjin.cafe.global.dto.IdResponse;
import com.kimseungjin.cafe.support.controller.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

@WebMvcTest(
        controllers = ProductController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ProductControllerTest extends ControllerTest {

    @MockBean private ProductService productService;

    @DisplayName("createProduct 메소드는")
    @Nested
    class RegisterProduct {

        private final IdResponse<UUID> expected = new IdResponse<>(UUID.randomUUID());

        @BeforeEach
        void setup() {
            when(productService.registerProduct(any(ProductRequest.class))).thenReturn(expected);
        }

        @DisplayName("정상적인 요청이 들어오면")
        @Nested
        class WhenRequestIsValid {

            private final ProductRequest productRequest = ProductRequestFixture.SUCCESS_REQUEST1.toRequest();

            @DisplayName("상품이 저장된다")
            @Test
            void saveProduct() throws Exception {
                final ResultActions perform = mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer asdfawefawef.awfeagrersghserth.heatrhareweahearhear")
                                .content(
                                        toRequestBody(productRequest)));

                perform.andExpect(status().isCreated())
                        .andExpect(jsonPath("$.data.id").value(expected.getId().toString()));
            }
        }

        @DisplayName("잘못된 데이터가 들어오면")
        @Nested
        class WhenRequestIsInvalid {

            private final ProductRequest productRequest = ProductRequestFixture.FAILURE_REQUEST1.toRequest();

            @DisplayName("400 에러가 발생한다.")
            @Test
            void saveProduct() throws Exception {
                final ResultActions perform = mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer asdfawefawef.awfeagrersghserth.heatrhareweahearhear")
                                .content(
                                        toRequestBody(productRequest)));

                perform.andExpect(status().isBadRequest());
            }
        }
    }
}
