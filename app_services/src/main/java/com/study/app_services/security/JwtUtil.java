package com.study.app_services.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    private final byte[] secret;
    private final long expirationMillis;

    public JwtUtil(byte[] secret, long expirationMillis) {
        this.secret = secret;
        this.expirationMillis = expirationMillis;
    }

    private String base64UrlEncode(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    }

    private byte[] base64UrlDecode(String input) {
        return Base64.getUrlDecoder().decode(input);
    }

    private byte[] hmacSha256(byte[] data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret, "HmacSHA256"));
        return mac.doFinal(data);
    }

    public String generateToken(String subject, String roles) {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        long exp = Instant.now().toEpochMilli() + expirationMillis;
        String payloadJson = "{\"sub\":\"" + subject + "\",\"roles\":\"" + roles + "\",\"exp\":" + exp + "}";
        String header = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
        String signingInput = header + "." + payload;
        try {
            String signature = base64UrlEncode(hmacSha256(signingInput.getBytes(StandardCharsets.UTF_8)));
            return signingInput + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String signingInput = parts[0] + "." + parts[1];
            String expectedSig = base64UrlEncode(hmacSha256(signingInput.getBytes(StandardCharsets.UTF_8)));
            if (!expectedSig.equals(parts[2])) return false;
            String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
            long exp = Long.parseLong(extractJsonValue(payloadJson, "exp"));
            return Instant.now().toEpochMilli() < exp;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return null;
        String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
        return extractJsonValue(payloadJson, "sub");
    }

    public String extractRoles(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return null;
        String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
        return extractJsonValue(payloadJson, "roles");
    }

    private String extractJsonValue(String json, String key) {
        String k = "\"" + key + "\"";
        int i = json.indexOf(k);
        if (i < 0) return null;
        int colon = json.indexOf(":", i + k.length());
        int start = colon + 1;
        char c = json.charAt(start);
        if (c == '"') {
            int end = json.indexOf("\"", start + 1);
            return json.substring(start + 1, end);
        } else {
            int endComma = json.indexOf(",", start);
            int endBrace = json.indexOf("}", start);
            int end = endComma > 0 ? Math.min(endComma, endBrace) : endBrace;
            return json.substring(start, end);
        }
    }
}
