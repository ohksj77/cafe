package com.kimseungjin.cafe.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode(of = {"phoneNumber", "password"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;
}
