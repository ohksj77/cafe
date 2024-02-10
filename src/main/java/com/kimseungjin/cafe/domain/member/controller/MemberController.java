package com.kimseungjin.cafe.domain.member.controller;

import com.kimseungjin.cafe.domain.member.dto.SignInRequest;
import com.kimseungjin.cafe.domain.member.service.MemberService;
import com.kimseungjin.cafe.global.dto.BaseResponse;
import com.kimseungjin.cafe.global.dto.IdResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<IdResponse<UUID>> signin(@RequestBody final SignInRequest signinRequest) {
        return BaseResponse.successOf(HttpStatus.CREATED, memberService.signIn(signinRequest));
    }
}
