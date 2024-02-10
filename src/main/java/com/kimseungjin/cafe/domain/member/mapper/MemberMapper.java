package com.kimseungjin.cafe.domain.member.mapper;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.domain.member.entity.Member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MemberMapper {

    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "password", source = "password")
    Member toEntity(final SignupRequest signupRequest);
}
