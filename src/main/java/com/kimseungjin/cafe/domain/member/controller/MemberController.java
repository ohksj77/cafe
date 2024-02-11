package com.kimseungjin.cafe.domain.member.controller;

import com.kimseungjin.cafe.config.security.jwt.JwtToken;
import com.kimseungjin.cafe.domain.member.dto.CredentialRequest;
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
    public BaseResponse<IdResponse<UUID>> signup(
            @RequestBody @Valid final CredentialRequest credentialRequest) {
        return BaseResponse.successOf(HttpStatus.CREATED, memberService.signup(credentialRequest));
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<JwtToken> login(
            @RequestBody @Valid final CredentialRequest credentialRequest) {
        return BaseResponse.successOf(HttpStatus.OK, memberService.login(credentialRequest));
    }
}
