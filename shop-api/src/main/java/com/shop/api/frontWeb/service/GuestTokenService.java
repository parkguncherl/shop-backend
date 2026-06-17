package com.shop.api.frontWeb.service;

import com.shop.api.config.JwtTokenProvider;
import com.shop.api.utils.CommUtil;
import com.shop.core.biz.partner.dao.PartnerDao;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.entity.GuestRateLimit;
import com.shop.core.frontWeb.dao.GuestRateLimitDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.vo.request.GuestTokenRequest;
import com.shop.core.frontWeb.vo.response.GuestTokenResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestTokenService {

    private final GuestTokenDao guestTokenDao;
    private final GuestRateLimitDao guestRateLimitDao;
    private final JwtTokenProvider jwtTokenProvider;

    private static final int RATE_LIMIT_COUNT = 100;
    private final PartnerDao partnerDao;

    /**
     * Guest Token 발급
     */
    public GuestTokenResponse.GuestTokenInfo issueGuestToken(GuestTokenRequest.Issue request) {

        String guestId    = "GUEST_" + UUID.randomUUID().toString().replace("-", "");
        Integer partnerId = 1; // 기본값이 1

        String mainDomain = CommUtil.extractDomainParts(request.getCurrentUrl())[0];
        String subDomain = CommUtil.extractDomainParts(request.getCurrentUrl())[1];
        if(mainDomain != null && !mainDomain.contains("localhost") || StringUtils.isNotBlank(mainDomain)) {
            if(StringUtils.isBlank(subDomain)){
                subDomain = "www";
            }
            PartnerResponse.Select partner = partnerDao.selectMyPartnerBySubDomain(mainDomain, subDomain);
            if(partner != null){
                partnerId = partner.getId();
                log.debug("<======= ContactRequest.PagingFilter: {}", partner);
            }
        }

        String guestToken = jwtTokenProvider.createGuestToken(guestId, partnerId);
        LocalDateTime expireDate = LocalDateTime.now().plusDays(30);

        GuestTokenResponse.GuestTokenInfo guestTokenInfo = new GuestTokenResponse.GuestTokenInfo();
        guestTokenInfo.setGuestId(guestId);
        guestTokenInfo.setPartnerId(partnerId);
        guestTokenInfo.setGuestToken(guestToken);
        guestTokenInfo.setSubDomain(request.getSubDomain());
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

        // 동시 요청 레이스 컨디션 방지: 원자적 upsert 후 카운트 확인
        guestRateLimitDao.insertRateLimit(param);

        GuestRateLimit current = guestRateLimitDao.selectRateLimit(param);
        return current.getCount() <= RATE_LIMIT_COUNT;
    }
}
