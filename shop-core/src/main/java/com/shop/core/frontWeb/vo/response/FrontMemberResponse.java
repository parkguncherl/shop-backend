package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class FrontMemberResponse {

    @Getter
    @Setter
    @Schema(name = "FrontMemberResponseToken", description = "FO 소셜 로그인 토큰 응답", type = "object")
    public static class Token {

        @Schema(description = "회원 ID (tb_social_account.id)")
        private Long memberId;

        @Schema(description = "소셜 제공자 (kakao)")
        private String provider;

        @Schema(description = "이메일")
        private String email;

        @Schema(description = "닉네임")
        private String nickname;

        @Schema(description = "프로필 이미지")
        private String profileImage;

        @Schema(description = "Access Token")
        private String accessToken;

        @Schema(description = "Refresh Token")
        private String refreshToken;

        @Schema(description = "신규 가입 여부")
        private boolean isNewMember;

        @Schema(description = "파트너 ID")
        private Integer partnerId;
    }
}
