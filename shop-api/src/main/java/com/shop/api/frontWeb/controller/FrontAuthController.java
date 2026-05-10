package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.GuestTokenService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontWeb-auth")
@Tag(name = "FrontAuthController", description = "FO 인증 관련 API")
@RequiredArgsConstructor
public class FrontAuthController {

    private final GuestTokenService guestTokenService;

    @NotAuthRequired
    @PostMapping("/guest")
    @Operation(summary = "web-Guest Token 발급")
    public ApiResponse<GuestToken> issueGuestToken(HttpServletRequest request) {

        String clientIp = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        return new ApiResponse<>(ApiResultCode.SUCCESS,guestTokenService.issueGuestToken(clientIp, userAgent));
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null) return forwarded.split(",")[0].trim();
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null) return realIp;
        return request.getRemoteAddr();
    }
}