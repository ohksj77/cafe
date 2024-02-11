package com.kimseungjin.cafe.domain.product.mapper;

import com.kimseungjin.cafe.domain.product.dto.ProductRequest;
import com.kimseungjin.cafe.domain.product.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "barcode", source = "barcode")
    @Mapping(target = "expirationDate", source = "expirationDate")
    @Mapping(target = "productSize", source = "productSize")
    Product toEntity(final ProductRequest productRequest);
}
