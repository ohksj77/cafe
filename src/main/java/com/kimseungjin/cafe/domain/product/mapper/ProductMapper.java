package com.kimseungjin.cafe.domain.product.mapper;

import com.kimseungjin.cafe.domain.product.dto.ProductDetailResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.domain.product.entity.Product;
import com.kimseungjin.cafe.utils.HangulUtils;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = ComponentModel.SPRING, imports = HangulUtils.class)
public interface ProductMapper {

    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "category", source = "productRequest.category")
    @Mapping(target = "price", source = "productRequest.price")
    @Mapping(target = "cost", source = "productRequest.cost")
    @Mapping(target = "name", source = "productRequest.name")
    @Mapping(target = "description", source = "productRequest.description")
    @Mapping(target = "barcode", source = "productRequest.barcode")
    @Mapping(target = "expirationDate", source = "productRequest.expirationDate")
    @Mapping(target = "productSize", source = "productRequest.productSize")
    @Mapping(
            target = "chosung",
            expression = "java(HangulUtils.decompose(productRequest.getName()))")
    Product toEntity(final ProductRequest productRequest, final UUID ownerId);

    @Mapping(target = "products", source = "products.content", qualifiedByName = "toResponses")
    @Mapping(target = "hasNext", expression = "java(products.hasNext())")
    ProductPageResponse toProductPageResponse(final Page<Product> products);

    @Named("toResponses")
    @IterableMapping(elementTargetType = ProductResponse.class)
    List<ProductResponse> toResponses(final List<Product> products);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "category", source = "product.category")
    ProductResponse toResponse(final Product product);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "ownerId", source = "product.ownerId")
    @Mapping(target = "category", source = "product.category")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "cost", source = "product.cost")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "barcode", source = "product.barcode")
    @Mapping(target = "expirationDate", source = "product.expirationDate")
    @Mapping(target = "productSize", source = "product.productSize")
    ProductDetailResponse toProductDetailResponse(final Product product);
}
