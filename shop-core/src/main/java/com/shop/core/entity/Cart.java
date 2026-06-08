package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Cart", description = "장바구니 Entity")
public class Cart {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "게스트 토큰 ID (tb_guest_token.id)")
    private Long guestTokenId;

    @Schema(description = "소셜 계정 ID (회원 전환 시)")
    private Long socialAccountId;

    @Schema(description = "상태 (active | ordered | abandoned | merged)")
    private String status;

    @Schema(description = "쿠폰 코드")
    private String couponCode;

    @Schema(description = "만료 일시")
    private LocalDateTime expiresAt;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;

    @Schema(description = "수정 일시")
    private LocalDateTime uptTm;

    @Schema(description = "장바구니 아이템 목록")
    private List<CartItem> items;
}
