package com.kimseungjin.cafe.domain.member.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.kimseungjin.cafe.domain.member.exception.LoginFailedException;
import com.kimseungjin.cafe.global.audit.AuditListener;
import com.kimseungjin.cafe.global.audit.Auditable;
import com.kimseungjin.cafe.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.SoftDelete;

import java.util.UUID;

@Getter
@Entity
@SoftDelete
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Auditable {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Embedded private LoginInfo loginInfo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter @Embedded private BaseTime baseTime;

    @Builder
    public Member(final String phoneNumber, final String password) {
        this.loginInfo = new LoginInfo(phoneNumber, password);
        this.role = Role.ROLE_USER;
    }

    public void validatePassword(final String password) {
        if (!loginInfo.isPasswordEquals(password)) {
            throw new LoginFailedException();
        }
    }
}
