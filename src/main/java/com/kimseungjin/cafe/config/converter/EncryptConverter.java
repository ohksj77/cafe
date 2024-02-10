package com.kimseungjin.cafe.config.converter;

import com.kimseungjin.cafe.utils.EncryptUtils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class EncryptConverter implements AttributeConverter<String, String> {

    private final EncryptUtils encryptUtils;

    @Override
    public String convertToDatabaseColumn(final String attribute) {
        return encryptUtils.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(final String dbData) {
        return encryptUtils.decrypt(dbData);
    }
}
