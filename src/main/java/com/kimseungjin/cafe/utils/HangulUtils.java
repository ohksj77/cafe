package com.kimseungjin.cafe.utils;


public class HangulUtils {

    private static final char START_CHAR = '가';
    private static final char LAST_CHAR = '힣';
    private static final int HANGUL_COUNT = 21 * 28;
    private static final char[] CHOSUNG = {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
        'ㅎ'
    };

    public static String decompose(final String s) {
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            final char c = checkCharacter(ch);
            sb.append(c);
        }
        return sb.toString();
    }

    private static char checkCharacter(final char ch) {
        if (ch >= START_CHAR && ch <= LAST_CHAR) {
            int base = (ch - START_CHAR);
            int cho = base / HANGUL_COUNT;
            return CHOSUNG[cho];
        }
        return ch;
    }

    public static boolean isChosung(final String str) {
        final int chosungCount = str.chars().filter(s -> !isHangul((char) s)).toArray().length;
        return chosungCount == str.length();
    }

    public static boolean isHangul(final String str) {
        final int hangulCount = str.chars().filter(s -> isHangul((char) s)).toArray().length;
        return hangulCount == str.length();
    }

    private static boolean isHangul(final char ch) {
        return ch >= START_CHAR && ch <= LAST_CHAR;
    }
}
