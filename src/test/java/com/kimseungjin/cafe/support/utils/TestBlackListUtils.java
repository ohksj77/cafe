package com.kimseungjin.cafe.support.utils;

import com.kimseungjin.cafe.utils.BlackListUtils;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;
import java.util.Set;

@Primary
@TestComponent
public class TestBlackListUtils implements BlackListUtils {

    private final Set<String> blackList = new HashSet<>();

    @Override
    public void add(final String accessToken) {
        this.blackList.add(accessToken);
    }

    @Override
    public boolean hasKey(final String accessToken) {
        return this.blackList.contains(accessToken);
    }
}
