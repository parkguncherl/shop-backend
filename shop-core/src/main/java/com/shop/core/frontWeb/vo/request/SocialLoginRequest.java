package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class SocialLoginRequest {

    @Getter
    @Setter
    @Schema(name = "SocialLoginRequestCallback", description = "소셜 로그인 콜백 요청", type = "object")
    public static class Callback {

        @Schema(description = "소셜 제공자 (kakao | naver | google)", required = true)
        private String provider;

        @Schema(description = "소셜 제공자 고유 ID", required = true)
        private String providerId;

        @Schema(description = "소셜 계정 이메일")
        private String email;

        @Schema(description = "소셜 닉네임")
        private String nickname;

        @Schema(description = "프로필 이미지 URL")
        private String profileImage;

        @Schema(description = "게스트 ID (장바구니 병합용)")
        private String guestId;
    }
}
