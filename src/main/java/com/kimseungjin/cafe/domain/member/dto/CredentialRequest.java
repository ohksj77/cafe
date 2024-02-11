package com.kimseungjin.cafe.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRequest {

    @Schema(
            description = "전화번호",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "010-1234-5678")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String phoneNumber;

    @Schema(
            description = "비밀번호",
            nullable = false,
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1234")
    @NotBlank private String password;
}
