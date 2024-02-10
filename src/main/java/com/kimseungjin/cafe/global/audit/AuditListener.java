package com.kimseungjin.cafe.global.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.util.Optional;

public class AuditListener {

    @PrePersist
    public void setCreatedAt(final Auditable auditable) {
        final BaseTime baseTime =
                Optional.ofNullable(auditable.getBaseTime()).orElseGet(BaseTime::new);
        auditable.setBaseTime(baseTime);
    }

    @PreUpdate
    public void setUpdatedAt(final Auditable auditable) {
        auditable.getBaseTime().update();
    }

    @PreRemove
    public void setDeletedAt(final Auditable auditable) {
        auditable.getBaseTime().delete();
    }
}
