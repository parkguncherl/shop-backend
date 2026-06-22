package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.FrontLoginService;
import com.shop.api.frontWeb.service.MemberJwtService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.SocialLoginRequest;
import com.shop.core.frontWeb.vo.response.FrontMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/frontWeb/login")
@Tag(name = "FrontLoginController", description = "FO 소셜 로그인 API")
@RequiredArgsConstructor
public class FrontLoginController {

    private final FrontLoginService frontLoginService;
    private final MemberJwtService memberJwtService;

    @NotAuthRequired
    @DeleteMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "FO 멤버 JWT 검증 후 개인정보 마스킹 처리")
    public ApiResponse<Void> withdraw(
            @Parameter(description = "소셜 계정 ID") @RequestParam Long socialAccountId,
            HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasLength(authHeader) || !authHeader.startsWith("Bearer ")) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_TOKEN);
        }
        String token = authHeader.replace("Bearer ", "");
        if (!memberJwtService.isValidToken(token)) {
            return new ApiResponse<>(ApiResultCode.TOKEN_UNAVAILABLE);
        }
        Long tokenMemberId = memberJwtService.getMemberIdFromToken(token);
        if (tokenMemberId == null || !tokenMemberId.equals(socialAccountId)) {
            return new ApiResponse<>(ApiResultCode.FAIL, "본인 계정만 탈퇴할 수 있습니다.");
        }

        frontLoginService.withdraw(socialAccountId);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 소셜 로그인 콜백
     * - Next.js next-auth 에서 카카오/네이버/구글 인증 완료 후 호출
     * - tb_social_account 기준으로 회원 조회/생성 후 서비스 JWT 반환
     */
    @NotAuthRequired
    @PostMapping("/social/callback")
    @Operation(summary = "소셜 로그인 콜백 (카카오 | 네이버 | 구글)",
               description = """
                       next-auth 소셜 인증 완료 후 프론트에서 호출.
                       tb_social_account 에서 기존 계정 조회 → 없으면 신규 등록.
                       서비스 JWT(accessToken, refreshToken) 반환.
                       """)
    public ApiResponse<FrontMemberResponse.Token> socialLoginCallback(
            @RequestBody SocialLoginRequest.Callback request) {
        try {
            FrontMemberResponse.Token result = frontLoginService.socialLogin(request);
            return new ApiResponse<>(ApiResultCode.SUCCESS, result);
        } catch (Exception e) {
            log.error("소셜 로그인 실패: provider={}, error={}", request.getProvider(), e.getMessage(), e);
            return new ApiResponse<>(ApiResultCode.FAIL, "소셜 로그인 처리 중 오류가 발생했습니다.");
        }
    }
}
