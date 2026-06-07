package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "CartItem", description = "장바구니 아이템 Entity")
public class CartItem {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "장바구니 ID")
    private Long cartId;

    @Schema(description = "상품 상세 ID (tb_product_det.id) — product_id 없이 JOIN으로 상품 조회")
    private Long productDetId;

    @Schema(description = "수량")
    private Integer quantity;

    @Schema(description = "담을 당시 가격 스냅샷")
    private BigDecimal unitPrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "옵션 스냅샷 JSON")
    private String optionsSnapshot;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;

    @Schema(description = "수정 일시")
    private LocalDateTime uptTm;

    // ── JOIN 조회 시 추가 정보 (tb_product_det → tb_product) ──────────
    @Schema(description = "상품 ID (tb_product.id)")
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
