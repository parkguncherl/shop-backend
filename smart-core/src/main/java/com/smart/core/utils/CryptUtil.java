package com.smart.core.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * Description : 암복호화 유틸리티
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
public class CryptUtil {

    public static String getHash(String target, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // digest반복횟수(1000번은 최소 권장횟수)
        int ITERATION_NUMBER = 1000;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(target.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < ITERATION_NUMBER; i++) {
            digest.reset();
            input = digest.digest(input);
        }

        return Base64.encodeBase64String(input);
    }

}
