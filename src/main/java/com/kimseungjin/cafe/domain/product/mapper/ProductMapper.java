package com.kimseungjin.cafe.domain.product.mapper;

import com.kimseungjin.cafe.domain.product.dto.ProductPageResponse;
import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.dto.ProductResponse;
import com.kimseungjin.cafe.domain.product.entity.Product;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = ComponentModel.SPRING)
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
}
