package com.kimseungjin.cafe.domain.member.entity;

import com.kimseungjin.cafe.config.converter.EncryptConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

    @Convert(converter = EncryptConverter.class)
    @Column(nullable = false)
    private String password;

    public boolean isPasswordEquals(final String password) {
        return this.password.equals(password);
    }
}
