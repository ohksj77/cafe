package com.kimseungjin.cafe.domain.product.mapper;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import java.util.UUID;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "category", source = "productRequest.category")
    @Mapping(target = "price", source = "productRequest.price")
    @Mapping(target = "cost", source = "productRequest.cost")
    @Mapping(target = "name", source = "productRequest.name")
    @Mapping(target = "description", source = "productRequest.description")
    @Mapping(target = "barcode", source = "productRequest.barcode")
    @Mapping(target = "expirationDate", source = "productRequest.expirationDate")
    @Mapping(target = "productSize", source = "productRequest.productSize")
    @Mapping(target = "ownerId", source = "ownerId")
    Product toEntity(final ProductRequest productRequest, final UUID ownerId);
}
