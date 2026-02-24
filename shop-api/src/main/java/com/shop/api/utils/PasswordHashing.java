package com.shop.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordHashing {
    private static final BCryptPasswordEncoder enc = new BCryptPasswordEncoder(12);

    public static String hash(String raw) { return enc.encode(raw); }
    public static boolean matches(String raw, String hash) { return enc.matches(raw, hash); }
}