package com.shop.api.frontWeb.service;

import com.shop.api.config.JwtTokenProvider;
import com.shop.core.entity.GuestRateLimit;
import com.shop.core.frontWeb.dao.GuestRateLimitDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.vo.request.GuestTokenRequest;
import com.shop.core.frontWeb.vo.response.GuestTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public GuestTokenResponse.GuestTokenInfo issueGuestToken(GuestTokenRequest.Issue request) {

        String guestId    = "GUEST_" + UUID.randomUUID().toString().replace("-", "");
        String guestToken = jwtTokenProvider.createGuestToken(guestId, request.getSubDomain());
        LocalDateTime expireDate = LocalDateTime.now().plusDays(30);

        GuestTokenResponse.GuestTokenInfo guestTokenInfo = new GuestTokenResponse.GuestTokenInfo();
        guestTokenInfo.setGuestId(guestId);
        guestTokenInfo.setGuestToken(guestToken);
        guestTokenInfo.setClientIp(request.getClientIp());
        guestTokenInfo.setUserAgent(request.getUserAgent());
        guestTokenInfo.setDeviceType(request.getDeviceType());
        guestTokenInfo.setOs(request.getOs());
        guestTokenInfo.setBrowser(request.getBrowser());
        guestTokenInfo.setRefererUrl(request.getRefererUrl());
        guestTokenInfo.setUtmSource(request.getUtmSource());
        guestTokenInfo.setUtmMedium(request.getUtmMedium());
        guestTokenInfo.setUtmCampaign(request.getUtmCampaign());
        guestTokenInfo.setFbclid(request.getFbclid());
        guestTokenInfo.setUtmContent(request.getUtmContent());  // ← 추가
        guestTokenInfo.setCurrentUrl(request.getCurrentUrl());
        guestTokenInfo.setExpireDate(expireDate);

        guestTokenDao.insertGuestToken(guestTokenInfo);

        return guestTokenInfo;
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
