package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "SocialAccount", description = "소셜 로그인 회원 Entity (tb_social_account) - 회원 테이블로 사용")
public class SocialAccount {

    @Schema(description = "PK (회원 ID로 사용)")
    private Long id;

    @Schema(description = "소셜 제공자 (kakao | naver | google)")
    private String provider;

    @Schema(description = "소셜 제공자 고유 ID")
    private String providerId;

    @Schema(description = "소셜 계정 이메일")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "프로필 이미지 URL")
    private String profileImage;

    @Schema(description = "상태 (active | withdrawn)")
    private String status;

    @Schema(description = "파트너 ID")
    private Integer partnerId;

    @Schema(description = "최종 로그인 일시")
    private LocalDateTime lastLoginTm;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;

    @Schema(description = "수정 일시")
    private LocalDateTime uptTm;
}
