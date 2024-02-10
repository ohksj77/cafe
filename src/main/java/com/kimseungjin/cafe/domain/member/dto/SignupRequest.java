package com.kimseungjin.cafe.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String phoneNumber;

    @NotBlank private String password;
}
