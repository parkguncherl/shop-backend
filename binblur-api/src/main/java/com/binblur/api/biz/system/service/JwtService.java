package com.binblur.api.biz.system.service;

import com.binblur.core.biz.system.dao.AuthTokenDao;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.entity.AuthToken;
import com.binblur.core.entity.JwtAuthToken;
import com.binblur.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 * Description :
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@Service
public class JwtService {

    @Value("${jwt.access.token.secret.key}")
    private String ACCESS_SECRET_KEY;

    @Value("${jwt.access.token.expiration.time}")
    private Integer ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh.token.expiration.time}")
    private Integer REFRESH_TOKEN_EXPIRATION_TIME;

    public static final String JWT_KEY_LOINGID = "loginId";
    public static final String JWT_KEY_MEMBER_ID = "userId";
    public static final String JWT_KEY_MEMBER_NAME = "userName";
    public static final String JWT_KEY_AUTH_CD = "authCd";
    public static final String JWT_KEY_LANGUAGE_CD = "languageCd";
    public static final String JWT_KEY_LOGIN_LANGUAGE = "loginLanguage";

    @Autowired
    private AuthTokenDao authTokenDao;

    @Autowired
    private ContactService contactService;
    /**
     * JWT Token 생성
     * @param userResponse
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public JwtAuthToken createAuthToken(UserResponse.SelectByLoginId userResponse) {
        LocalDateTime accessTokenExpireDate = LocalDateTime.now().plusMinutes(ACCESS_TOKEN_EXPIRATION_TIME);
        LocalDateTime refreshTokenExpireDate = LocalDateTime.now().plusMinutes(REFRESH_TOKEN_EXPIRATION_TIME);
        // 새로운 토큰 생성
        JwtAuthToken token = new JwtAuthToken();
        token.setAccessToken(buildJwtToken(userResponse, accessTokenExpireDate));
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setAccessTokenExpireDate(accessTokenExpireDate);
        token.setRefreshTokenExpireDate(refreshTokenExpireDate);

        return token;
    }

    /**
     * JWT Token 갱신
     * @param accessToken
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public JwtAuthToken refreshAuthToken(String accessToken) {

        // 토큰정보 조회
        AuthToken lastToken = authTokenDao.selectAuthTokenByAccessToken(accessToken);
        if(lastToken == null) {
            log.warn(">>>>>> 최종 토큰값 NOT FOUND");
            return new JwtAuthToken(HttpStatus.NOT_FOUND.name());
        }

        // RefreshToken 만료기간 확인 contact  로그 정보로 다시함 확인
        if(StringUtils.equals("Y", contactService.isOverTime(lastToken.getUserId()))){
            return new JwtAuthToken(HttpStatus.REQUEST_TIMEOUT.name());
        }

        // RefreshToken 만료기간 확인
        if (lastToken.getRefreshTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            log.warn(">>>>>> Refresh Token 만료");
            return new JwtAuthToken(HttpStatus.REQUEST_TIMEOUT.name());
        }


        // 파라미터로 수신한 토큰정보에서 회원정보 추출
        User user = getUser(accessToken);

        // 새로운 토큰 생성
        LocalDateTime accessTokenExpireDate = LocalDateTime.now().plusMinutes(ACCESS_TOKEN_EXPIRATION_TIME);
        LocalDateTime refreshTokenExpireDate = LocalDateTime.now().plusMinutes(REFRESH_TOKEN_EXPIRATION_TIME);

        JwtAuthToken newJwtAuthToken = new JwtAuthToken(HttpStatus.OK.name());
        newJwtAuthToken.setAccessToken(buildJwtToken(user, accessTokenExpireDate));
        newJwtAuthToken.setRefreshToken(UUID.randomUUID().toString());
        newJwtAuthToken.setAccessTokenExpireDate(accessTokenExpireDate);
        newJwtAuthToken.setRefreshTokenExpireDate(refreshTokenExpireDate);

        // API Token 등록
        AuthToken authToken = new AuthToken();
        authToken.setUserId(user.getId());
        authToken.setAccessToken(newJwtAuthToken.getAccessToken());
        authToken.setAccessTokenExpireDateTime(accessTokenExpireDate);
        authToken.setRefreshToken(newJwtAuthToken.getRefreshToken());
        authToken.setRefreshTokenExpireDateTime(refreshTokenExpireDate);

        authTokenDao.createAuthToken(authToken);

        return newJwtAuthToken;
    }

    /**
     * JWT Access 토큰 Builder
     * @param user
     * @param accessTokenExpireDate
     * @return
     */
    private String buildJwtToken(User user, LocalDateTime accessTokenExpireDate) {
        byte[] keyBytes = (ACCESS_SECRET_KEY).getBytes(StandardCharsets.UTF_8);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(Date.from(accessTokenExpireDate.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(JWT_KEY_LOINGID, user.getLoginId())
                .claim(JWT_KEY_MEMBER_ID, user.getId())
                .claim(JWT_KEY_MEMBER_NAME, user.getUserNm())
                .claim(JWT_KEY_AUTH_CD, user.getAuthCd())
                .claim(JWT_KEY_LANGUAGE_CD, user.getLanguageCode())
                .claim(JWT_KEY_LOGIN_LANGUAGE, user.getLoginLanguage())
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰의 회원 정보를 반환
     * @param accessToken
     * @return
     */
    public User getUser(String accessToken) {
        byte[] keyBytes = (ACCESS_SECRET_KEY).getBytes(StandardCharsets.UTF_8);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(keyBytes)
                .build()
                .parseClaimsJws(accessToken);

        User user = new User();
        user.setLoginId((String) claims.getBody().get(JWT_KEY_LOINGID));
        user.setId((Integer) claims.getBody().get(JWT_KEY_MEMBER_ID));
        user.setUserNm((String) claims.getBody().get(JWT_KEY_MEMBER_NAME));
        user.setAuthCd((String) claims.getBody().get(JWT_KEY_AUTH_CD));
        user.setLanguageCode((String) claims.getBody().get(JWT_KEY_LANGUAGE_CD));
        user.setLoginLanguage((String) claims.getBody().get(JWT_KEY_LOGIN_LANGUAGE));

        return user;
    }

    /**
     * 토큰 유효성 체크
     * @param accessToken
     * @return
     */
    public boolean isValidToken(String accessToken) {
        byte[] keyBytes = (ACCESS_SECRET_KEY).getBytes(StandardCharsets.UTF_8);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(keyBytes)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
