package com.shop.core.entity;

import com.shop.core.enums.CartStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TB_CART 엔티티 — 장바구니 아이템 1행 = 상품 1종
 * (TB_CART + TB_CART_ITEM 통합 구조)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Cart", description = "장바구니 Entity (아이템 통합)")
public class Cart {

    @Schema(description = "PK (장바구니 아이템 ID)")
    private Long id;

    @Schema(description = "소셜 계정 ID — 회원 키")
    private Long socialAccountId;

    @Schema(description = "게스트 토큰 ID — 비회원 키")
    private Long guestTokenId;

    @Schema(description = "상품 상세 ID (TB_PRODUCT_DET.id)")
    private Long productDetId;

    @Schema(description = "수량")
    private Integer quantity;

    @Schema(description = "담을 당시 가격 스냅샷")
    private BigDecimal unitPrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "상태 (A=활성, O=주문완료)")
    private CartStatus status;

    @Schema(description = "삭제 여부")
    private String delYn;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;

    @Schema(description = "수정 일시")
    private LocalDateTime uptTm;

    // ── JOIN 조회 시 추가 정보 ──────────────────────────────
    @Schema(description = "상품 ID")
    private Long productId;

    @Schema(description = "상품명")
    private String productName;

    @Schema(description = "상품 대표 이미지")
    private String productImage;

    @Schema(description = "사이즈")
    private String productDetSize;

    @Schema(description = "색상")
    private String productDetColor;

    @Schema(description = "할인율")
    private Integer skuDiscountRate;
}
