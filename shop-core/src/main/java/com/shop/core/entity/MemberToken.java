package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "MemberToken", description = "FO 회원 토큰 Entity (tb_member_token)")
public class MemberToken {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "회원 ID (tb_social_account.id)")
    private Long memberId;

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Refresh Token")
    private String refreshToken;

    @Schema(description = "Access Token 만료 일시")
    private LocalDateTime accessTokenExpTm;

    @Schema(description = "Refresh Token 만료 일시")
    private LocalDateTime refreshTokenExpTm;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;
}
