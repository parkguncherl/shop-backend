package com.shop.api.frontWeb.service;

import com.shop.core.entity.MemberToken;
import com.shop.core.entity.SocialAccount;
import com.shop.core.frontWeb.dao.SocialAccountDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * FO 소셜 회원 전용 JWT 서비스
 * - 회원 식별 기준: tb_social_account.id
 * - BO JwtService 와 완전 분리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJwtService {

    private final SocialAccountDao socialAccountDao;

    @Value("${jwt.access.token.secret.key}")
    private String secretKey;

    @Value("${jwt.access.token.expiration.time}")
    private Integer accessTokenExpMin;

    @Value("${jwt.refresh.token.expiration.time}")
    private Integer refreshTokenExpMin;

    private static final String CLAIM_TYPE      = "type";
    private static final String CLAIM_MEMBER_ID = "memberId";
    private static final String CLAIM_EMAIL     = "email";
    private static final String CLAIM_PROVIDER  = "provider";
    private static final String TOKEN_TYPE      = "MEMBER";

    /**
     * SocialAccount 기반 토큰 발급 후 DB upsert
     */
    public MemberToken issueToken(SocialAccount account) {
        LocalDateTime accessExp  = LocalDateTime.now().plusMinutes(accessTokenExpMin);
        LocalDateTime refreshExp = LocalDateTime.now().plusMinutes(refreshTokenExpMin);

        String accessToken  = buildAccessToken(account, accessExp);
        String refreshToken = UUID.randomUUID().toString();

        MemberToken memberToken = MemberToken.builder()
                .memberId(account.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpTm(accessExp)
                .refreshTokenExpTm(refreshExp)
                .build();

        socialAccountDao.upsertMemberToken(memberToken);
        return memberToken;
    }

    /**
     * Access Token 유효성 검증
     */
    public boolean isValidToken(String accessToken) {
        try {
            Claims claims = parseClaims(accessToken);
            return TOKEN_TYPE.equals(claims.get(CLAIM_TYPE));
        } catch (Exception e) {
            log.debug("FO 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Access Token 에서 SocialAccount ID 추출
     */
    public Long getMemberIdFromToken(String accessToken) {
        try {
            Claims claims = parseClaims(accessToken);
            return ((Number) claims.get(CLAIM_MEMBER_ID)).longValue();
        } catch (Exception e) {
            log.warn("토큰에서 memberId 추출 실패: {}", e.getMessage());
            return null;
        }
    }

    // ── private ───────────────────────────────────────────────────────

    private String buildAccessToken(SocialAccount account, LocalDateTime expireDate) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .expiration(Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant()))
                .issuedAt(new Date())
                .claim(CLAIM_TYPE,      TOKEN_TYPE)
                .claim(CLAIM_MEMBER_ID, account.getId())
                .claim(CLAIM_EMAIL,     account.getEmail())
                .claim(CLAIM_PROVIDER,  account.getProvider())
                .signWith(getSigningKey())
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
