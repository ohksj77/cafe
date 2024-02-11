package com.kimseungjin.cafe.utils;

public interface BlackListUtils {

    void add(final String accessToken);

    boolean hasKey(final String key);
}
