package com.shop.api.config;

import com.shop.core.biz.partner.dao.PartnerDao;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final PartnerDao partnerDao;
    @Value("${jwt.access.token.secret.key}")  // ← 기존 키 이름으로 변경
    private String secretKey;

    public JwtTokenProvider(PartnerDao partnerDao) {
        this.partnerDao = partnerDao;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Guest Token 생성
    public String createGuestToken(String guestId, Integer partnerId, String clientIp) {

        return Jwts.builder()
                .subject(guestId)
                .claim("type", "GUEST")
                .claim("partnerId", partnerId)
                .claim("clientIp", clientIp)
                .issuedAt(new Date())
                .expiration(Date.from(
                        LocalDateTime.now()
                                .plusDays(30)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                ))
                .signWith(getSigningKey())
                .compact();
    }

    // Token 검증
    public boolean validateGuestToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return "GUEST".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    // Guest ID 추출
    public String getGuestId(String token) {
        return parseClaims(token).getSubject();
    }


    public Integer getPartnerId(String token) {
        Claims claims = parseClaims(token);
        return claims.get("partnerId", Integer.class);
    }

    // 클라이언트 IP 추출 (토큰 발급 시점의 IP, 구 토큰은 claim 없음 → null)
    public String getClientIp(String token) {
        Claims claims = parseClaims(token);
        return claims.get("clientIp", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}