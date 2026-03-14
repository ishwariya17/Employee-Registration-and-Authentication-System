package com.employeesystem.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password hashing for secure storage. Uses SHA-256 (no extra dependencies).
 * Stored hashes are 64-char hex; supports legacy plain-text for backward compatibility.
 */
public final class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";

    private PasswordUtil() {}

    public static String hash(String plainPassword) {
        if (plainPassword == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static boolean verify(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) return false;
        // New users: stored password is 64-char hex (SHA-256)
        if (storedPassword.length() == 64 && isHex(storedPassword)) {
            return hash(plainPassword).equalsIgnoreCase(storedPassword);
        }
        // Legacy: plain text (e.g. admin123 from seed data)
        return plainPassword.equals(storedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static boolean isHex(String s) {
        return s.matches("[0-9a-fA-F]{64}");
    }
}
