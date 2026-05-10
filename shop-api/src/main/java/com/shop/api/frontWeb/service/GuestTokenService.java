package com.shop.api.frontWeb.service;

import com.shop.api.biz.system.service.JwtService;
import com.shop.api.config.JwtTokenProvider;
import com.shop.core.entity.GuestRateLimit;
import com.shop.core.entity.GuestToken;
import com.shop.core.frontWeb.dao.GuestRateLimitDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.vo.response.GuestTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class GuestTokenService {

    private final GuestTokenDao guestTokenDao;
    private final GuestRateLimitDao guestRateLimitDao;
    private final JwtTokenProvider jwtTokenProvider;

    private static final int RATE_LIMIT_COUNT = 100;

    /**
     * Guest Token 발급
     */
    public GuestToken issueGuestToken(String clientIp, String userAgent) {

        String guestId = "GUEST_" + UUID.randomUUID().toString().replace("-", "");
        String guestToken = jwtTokenProvider.createGuestToken(guestId);
        LocalDateTime expireDate = LocalDateTime.now().plusDays(30);

        GuestToken guestTokenInsert = GuestToken.builder()
                .guestId(guestId)
                .guestToken(guestToken)
                .clientIp(clientIp)
                .userAgent(userAgent)
                .expireDate(expireDate)
                .build();
        guestTokenDao.insertGuestToken(guestTokenInsert);

        return guestTokenInsert;
    }

    /**
     * Rate Limiting 체크
     */
    public boolean checkRateLimit(String guestId) {
        String minuteKey = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        GuestRateLimit param = GuestRateLimit.builder()
                .guestId(guestId)
                .minuteKey(minuteKey)
                .build();

        GuestRateLimit existing = guestRateLimitDao.selectRateLimit(param);

        if (existing == null) {
            guestRateLimitDao.insertRateLimit(param);
            return true;
        }

        if (existing.getCount() >= RATE_LIMIT_COUNT) {
            return false;
        }

        guestRateLimitDao.incrementRateLimit(param);
        return true;
    }
}
