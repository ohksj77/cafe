package com.kimseungjin.cafe.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class EncryptUtils {

    private static final String BLANK = " ";
    private static final String STRING_SPLITTER = "\\s";
    private final AesBytesEncryptor aesBytesEncryptor;

    public String encrypt(final String text) {
        final byte[] encrypt = aesBytesEncryptor.encrypt(text.getBytes(StandardCharsets.UTF_8));
        return byteArrayToString(encrypt);
    }

    public String decrypt(final String cipherText) {
        final byte[] decryptBytes = stringToByteArray(cipherText);
        final byte[] decrypt = aesBytesEncryptor.decrypt(decryptBytes);
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    private String byteArrayToString(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(b);
            sb.append(BLANK);
        }
        return sb.toString();
    }

    private byte[] stringToByteArray(final String byteString) {
        final String[] split = byteString.split(STRING_SPLITTER);
        final ByteBuffer buffer = ByteBuffer.allocate(split.length);
        for (String s : split) {
            buffer.put((byte) Integer.parseInt(s));
        }
        return buffer.array();
    }
}
