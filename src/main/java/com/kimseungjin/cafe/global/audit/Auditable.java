package com.kimseungjin.cafe.global.audit;


public interface Auditable {

    BaseTime getBaseTime();

    void setBaseTime(final BaseTime baseTime);
}
