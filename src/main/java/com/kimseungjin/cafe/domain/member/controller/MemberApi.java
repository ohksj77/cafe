package com.kimseungjin.cafe.domain.member.controller;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.domain.member.dto.CredentialRequest;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Tag(name = "Member")
public interface MemberApi {

    @Operation(summary = "회원 가입 API", description = "동일한 회원(전화번호로 구분)이 존재하지 않는 경우 회원을 저장합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "요청 성공"),
        @ApiResponse(
                responseCode = "409",
                description = "같은 전화번호의 회원이 존재합니다.",
                content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(
                responseCode = "400",
                description = "요청 데이터가 잘못되었습니다.",
                content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    BaseResponse<IdResponse<UUID>> signup(final CredentialRequest credentialRequest);

    @Operation(summary = "로그인 API", description = "아이디와 비밀번호를 확인하여 Jwt 토큰을 발급합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청 성공"),
        @ApiResponse(
                responseCode = "400",
                description = "요청 데이터가 잘못되었습니다.",
                content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    BaseResponse<JwtToken> login(final CredentialRequest credentialRequest);

    @Operation(summary = "로그아웃 API", description = "Jwt 토큰을 블랙리스트에 추가합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "요청 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 데이터가 잘못되었습니다.",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    void logout(final String bearerToken);
}
