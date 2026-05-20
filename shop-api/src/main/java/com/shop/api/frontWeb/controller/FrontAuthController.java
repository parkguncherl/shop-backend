package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.GuestTokenService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.GuestTokenRequest;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/frontWebAuth")
@Tag(name = "FrontAuthController", description = "FO 인증 관련 API")
@RequiredArgsConstructor
public class FrontAuthController {

    private final GuestTokenService guestTokenService;

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
        String origin = request.getHeader("Origin");  // https://www.gguanggu.com

        GuestTokenRequest.Issue issueRequest = GuestTokenRequest.Issue.builder()
                .clientIp(clientIp)
                .userAgent(userAgent)
                .deviceType(deviceType)
                .os(os)
                .browser(browser)
                .refererUrl(refererUrl)
                .currentUrl(currentUrl)
                .utmSource(utmSource)
                .utmMedium(utmMedium)
                .utmCampaign(utmCampaign)
                .utmContent(utmContent)
                .fbclid(fbclid)
                .subDomain(parseSubDomain(origin))
                .build();

        return new ApiResponse<>(ApiResultCode.SUCCESS,guestTokenService.issueGuestToken(issueRequest));
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
        if (origin == null) return "";
        // https://www.gguanggu.com → www
        // https://admin.gguanggu.com → admin
        // http://localhost:3000 → localhost
        String host = origin
                .replaceAll("https?://", "")  // http://, https:// 제거
                .replaceAll(":\\d+$", "");    // 포트 제거

        String[] parts = host.split("\\.");
        if (parts.length >= 3) { add
            return StringUtils.isBlank(parts[0]) ? "www" : parts[0] ;  // www, admin, shop 등
        }
        return "www";  // localhost
    }
}