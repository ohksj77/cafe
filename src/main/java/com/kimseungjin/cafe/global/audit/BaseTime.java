package com.kimseungjin.cafe.global.audit;

import jakarta.persistence.Embeddable;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Embeddable
public class BaseTime {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    protected BaseTime() {
        this.createdAt = LocalDateTime.now();
    }

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isActivated() {
        return Optional.ofNullable(this.deletedAt).isPresent();
    }
}
