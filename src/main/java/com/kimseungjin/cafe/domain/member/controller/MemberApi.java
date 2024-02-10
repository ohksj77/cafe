package com.kimseungjin.cafe.domain.member.controller;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Tag(name = "Member")
public interface MemberApi {

    @Operation(summary = "회원 가입 API", description = "동일한 회원(전화번호로 구분)이 존재하지 않는 경우 회원을 저장합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "요청 성공"),
        @ApiResponse(
                responseCode = "400",
                description = "요청 데이터가 잘못되었습니다.",
                content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    BaseResponse<IdResponse<UUID>> signup(final SignupRequest signupRequest);
}
