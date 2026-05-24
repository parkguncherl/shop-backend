package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.GuestTokenService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.GuestTokenRequest;
import com.shop.core.frontWeb.vo.response.GuestTokenResponse;
import org.apache.commons.lang3.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/frontWebAuth")
@Tag(name = "FrontAuthController", description = "FO 인증 관련 API")
@RequiredArgsConstructor
public class FrontAuthController {

    private final GuestTokenService guestTokenService;
    private static final String DEFAULT_SUB_DOMAIN = "www";

    @NotAuthRequired
    @PostMapping("/guest")
    @Operation(summary = "web-Guest Token 발급")
    public ApiResponse<GuestToken> issueGuestToken(HttpServletRequest request) {

        log.debug("issueGuestToken  start ==>");
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
        String origin = request.getHeader("Origin");  // https://www.gguanggu.com
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

        GuestTokenResponse.GuestTokenInfo result = guestTokenService.issueGuestToken(issueRequest);
        log.debug("issueGuestToken  end ==>", result);

        return new ApiResponse<>(ApiResultCode.SUCCESS,result);
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null) return forwarded.split(",")[0].trim();
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null) return realIp;
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
        if (StringUtils.isBlank(origin)) {
            return DEFAULT_SUB_DOMAIN;
        }

        try {
            String normalized = origin.startsWith("http")
                    ? origin
                    : "https://" + origin;

            URI uri = URI.create(normalized);
            String host = uri.getHost();

            if (StringUtils.isBlank(host)) {
                return DEFAULT_SUB_DOMAIN;
            }

            // localhost 또는 IP
            if ("localhost".equals(host) || host.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")) {
                return host;
            }

            String[] parts = host.split("\\.");

            // admin.gguanggu.com → admin
            // www.gguanggu.com → www
            if (parts.length >= 3) {
                return StringUtils.defaultIfBlank(parts[0], DEFAULT_SUB_DOMAIN);
            }

            return DEFAULT_SUB_DOMAIN;

        } catch (Exception e) {
            return DEFAULT_SUB_DOMAIN;
        }
    }
}