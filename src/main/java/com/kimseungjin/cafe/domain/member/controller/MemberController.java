package com.kimseungjin.cafe.domain.member.controller;

import com.kimseungjin.cafe.domain.member.dto.SignupRequest;
import com.kimseungjin.cafe.domain.member.service.MemberService;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<IdResponse<UUID>> signup(@RequestBody @Valid final SignupRequest signupRequest) {
        return BaseResponse.successOf(HttpStatus.CREATED, memberService.signup(signupRequest));
    }
}
