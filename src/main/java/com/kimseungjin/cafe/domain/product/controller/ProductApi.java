package com.kimseungjin.cafe.domain.product.controller;

import com.kimseungjin.cafe.domain.product.dto.ProductDetailResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(name = "Product")
public interface ProductApi {

    @Operation(summary = "상품 등록 API", description = "상품을 db에 저장합니다.")
    @ApiResponse(responseCode = "201", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    BaseResponse<IdResponse<UUID>> registerProduct(final ProductRequest productRequest);

    @Operation(summary = "상품 수정 API", description = "상품 정보 일부를 수정합니다.")
    @ApiResponse(responseCode = "204", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "403",
            description = "접근 권한이 없습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    void updateProduct(final UUID id, final ProductRequest productRequest);

    @Operation(summary = "상품 삭제 API", description = "상품을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "403",
            description = "접근 권한이 없습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    void removeProduct(final UUID id);

    @Operation(summary = "상품 목록 조회 API", description = "상품 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    BaseResponse<ProductPageResponse> getProducts(final Integer page);

    @Operation(summary = "상품 상세 조회 API", description = "상품 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    BaseResponse<ProductDetailResponse> getProductDetail(final UUID id);

    @Operation(summary = "상품 검색 API", description = "상품을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공")
    @ApiResponse(
            responseCode = "400",
            description = "요청 데이터가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = "인증 정보가 잘못되었습니다.",
            content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    @SecurityRequirement(name = "Authorization")
    BaseResponse<List<ProductResponse>> searchProducts(final String query);
}
