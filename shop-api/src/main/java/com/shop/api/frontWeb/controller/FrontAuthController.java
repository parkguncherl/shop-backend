package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.GuestTokenService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestReferrerLog;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.dao.GuestReferrerLogDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.vo.request.GuestTokenRequest;
import com.shop.core.frontWeb.vo.response.GuestTokenResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/frontWebAuth")
@Tag(name = "FrontAuthController", description = "FO 인증 관련 API")
@RequiredArgsConstructor
public class FrontAuthController {

    private final GuestTokenService guestTokenService;
    private final GuestTokenDao guestTokenDao;
    private final GuestReferrerLogDao guestReferrerLogDao;
    private static final String DEFAULT_SUB_DOMAIN = "www";

    @NotAuthRequired
    @PostMapping("/guest")
    @Operation(summary = "web-Guest Token 발급")
    public ApiResponse<GuestToken> issueGuestToken(HttpServletRequest request) {

        String clientIp    = getClientIp(request);
        String userAgent   = request.getHeader("User-Agent");
        String refererUrl  = request.getHeader("X-Referer-URL");   // ← 변경
        String currentUrl  = request.getHeader("X-Current-URL");   // ← 추가
        String utmSource   = request.getHeader("X-UTM-Source");    // ← 변경
        String utmMedium   = request.getHeader("X-UTM-Medium");    // ← 변경
        String utmCampaign = request.getHeader("X-UTM-Campaign");  // ← 변경
        String utmContent  = request.getHeader("X-UTM-Content");   // ← 변경
        String fbclid      = request.getHeader("X-Fbclid");        // ← 추가
        String deviceType  = parseDeviceType(userAgent);
        String os          = parseOs(userAgent);
        String browser     = parseBrowser(userAgent);
        String origin = request.getHeader("Origin");  // https://www.mapsiggun.com
        String subDomain = parseSubDomain(origin);

        GuestTokenRequest.Issue issueRequest = new GuestTokenRequest.Issue();
        issueRequest.setClientIp(clientIp);
        issueRequest.setUserAgent(userAgent);
        issueRequest.setDeviceType(deviceType);
        issueRequest.setOs(os);
        issueRequest.setBrowser(browser);
        issueRequest.setRefererUrl(refererUrl);
        issueRequest.setCurrentUrl(currentUrl);
        issueRequest.setUtmSource(utmSource);
        issueRequest.setUtmMedium(utmMedium);
        issueRequest.setUtmCampaign(utmCampaign);
        issueRequest.setUtmContent(utmContent);
        issueRequest.setFbclid(fbclid);
        issueRequest.setSubDomain(subDomain);

        String existingGuestToken = Arrays.stream(
                        Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])
                )
                .filter(c -> "guest_token".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        issueRequest.setExistingGuestToken(existingGuestToken); // ← VO에 필드 추가
        GuestTokenResponse.GuestTokenInfo result = guestTokenService.issueGuestToken(issueRequest);
        return new ApiResponse<>(ApiResultCode.SUCCESS,result);
    }

    private String getClientIp(HttpServletRequest request) {
        String realIp    = request.getHeader("X-Real-IP");
        log.debug("getClientIp  start =============>["+ realIp + "]");
        if (StringUtils.hasLength(realIp)) return realIp;
        String forwarded = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasLength(forwarded)) return forwarded.split(",")[0].trim();
        return request.getRemoteAddr();
    }

    private String parseDeviceType(String userAgent) {
        if (userAgent == null) return "unknown";
        String ua = userAgent.toLowerCase();
        if (ua.contains("mobile"))  return "mobile";
        if (ua.contains("tablet"))  return "tablet";
        return "desktop";
    }

    private String parseOs(String userAgent) {
        if (userAgent == null) return "unknown";
        String ua = userAgent.toLowerCase();
        if (ua.contains("windows")) return "Windows";
        if (ua.contains("android")) return "Android";
        if (ua.contains("iphone") || ua.contains("ipad")) return "iOS";
        if (ua.contains("mac"))     return "macOS";
        if (ua.contains("linux"))   return "Linux";
        return "unknown";
    }

    private String parseBrowser(String userAgent) {
        if (userAgent == null) return "unknown";
        String ua = userAgent.toLowerCase();
        if (ua.contains("edg"))     return "Edge";
        if (ua.contains("chrome"))  return "Chrome";
        if (ua.contains("safari"))  return "Safari";
        if (ua.contains("firefox")) return "Firefox";
        return "unknown";
    }

    private String parseSubDomain(String origin) {
        if (!StringUtils.hasText(origin)) {
            return DEFAULT_SUB_DOMAIN;
        }

        try {
            String normalized = origin.startsWith("http")
                    ? origin
                    : "https://" + origin;

            URI uri = URI.create(normalized);
            String host = uri.getHost();

            if (!StringUtils.hasText(host)) {
                return DEFAULT_SUB_DOMAIN;
            }

            // localhost 또는 IP
            if ("localhost".equals(host) || host.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")) {
                return host;
            }

            String[] parts = host.split("\\.");

            // admin.mapsiggun.com → admin
            // www.mapsiggun.com → www
            if (parts.length >= 3) {
                return StringUtils.hasText(parts[0]) ? parts[0] : DEFAULT_SUB_DOMAIN;
            }

            return DEFAULT_SUB_DOMAIN;

        } catch (Exception e) {
            return DEFAULT_SUB_DOMAIN;
        }
    }

    /**
     * 유입 경로 이력 적재
     * - 게스트 첫 방문 이후 유입 경로(utm)가 변경될 때마다 호출
     * - TB_GUEST_TOKEN 의 현재값은 프론트에서 별도로 업데이트,
     *   이 API 는 변경 이력만 TB_GUEST_REFERRER_LOG 에 쌓음
     */
    @NotAuthRequired
    @PostMapping("/referrer-log")
    @Operation(summary = "유입 경로 이력 적재",
               description = "UTM/Referrer 변경 시 이력 테이블(TB_GUEST_REFERRER_LOG)에 적재")
    public ApiResponse<Void> saveReferrerLog(HttpServletRequest request) {

        String guestId     = request.getHeader("X-Guest-Id");
        String utmSource   = request.getHeader("X-UTM-Source");
        String utmMedium   = request.getHeader("X-UTM-Medium");
        String utmCampaign = request.getHeader("X-UTM-Campaign");
        String utmContent  = request.getHeader("X-UTM-Content");
        String refererUrl  = request.getHeader("X-Referer-URL");
        String landingUrl  = request.getHeader("X-Current-URL");

        if (!StringUtils.hasText(guestId)) {
            return new ApiResponse<>(ApiResultCode.FAIL, "X-Guest-Id 헤더가 필요합니다.");
        }

        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) {
            return new ApiResponse<>(ApiResultCode.FAIL, "유효하지 않은 게스트 ID입니다.");
        }

        GuestReferrerLog log = new GuestReferrerLog();
        log.setGuestTokenId(guestToken.getId());
        log.setUtmSource(utmSource);
        log.setUtmMedium(utmMedium);
        log.setUtmCampaign(utmCampaign);
        log.setUtmContent(utmContent);
        log.setRefererUrl(refererUrl);
        log.setLandingUrl(landingUrl);
        guestReferrerLogDao.insertReferrerLog(log);

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 유입 경로 이력 조회
     * - guestId 로 해당 게스트의 전체 유입 이력 반환
     */
    @NotAuthRequired
    @GetMapping("/referrer-log/{guestId}")
    @Operation(summary = "유입 경로 이력 조회",
               description = "게스트 ID로 유입 경로 변경 이력 전체 조회")
    public ApiResponse<List<GuestReferrerLog>> getReferrerLogs(
            @PathVariable String guestId) {

        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) {
            return new ApiResponse<>(ApiResultCode.FAIL, "유효하지 않은 게스트 ID입니다.");
        }

        List<GuestReferrerLog> logs = guestReferrerLogDao.selectLogsByGuestTokenId(guestToken.getId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, logs);
    }
}