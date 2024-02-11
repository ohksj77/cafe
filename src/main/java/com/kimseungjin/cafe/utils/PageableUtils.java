package com.kimseungjin.cafe.utils;

import lombok.NoArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PageableUtils {

    private static final int PAGE_SIZE = 10;

    public static Pageable pageableFrom(final Integer page) {
        return PageRequest.of(page, PAGE_SIZE);
    }
}
