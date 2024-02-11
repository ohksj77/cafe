package com.kimseungjin.cafe.utils;

import java.util.UUID;

public interface BlackListUtils {

    void add(final String accessToken, final UUID memberId);

    boolean hasKey(final String key);
}
