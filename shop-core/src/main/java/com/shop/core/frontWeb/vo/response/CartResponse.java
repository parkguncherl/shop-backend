package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

    @Getter
    @Setter
    @Schema(name = "CartResponseCartInfo", description = "장바구니 정보", type = "object")
    public static class CartInfo {

        @Schema(description = "아이템 목록")
        private List<ItemInfo> items;

        @Schema(description = "총 상품 수")
        private int totalCount;

        @Schema(description = "총 금액")
        private BigDecimal totalPrice;
    }

    @Getter
    @Setter
    @Schema(name = "CartResponseItemInfo", description = "장바구니 아이템 정보", type = "object")
    public static class ItemInfo {

        @Schema(description = "장바구니 ID (TB_CART.id)")
        private Long cartId;

        @Schema(description = "상품 ID")
        private Long productId;

        @Schema(description = "상품명")
        private String productName;

        @Schema(description = "상품 대표 이미지")
        private String productImage;

        @Schema(description = "상품 상세 ID (tb_product_det.id)")
        private Long productDetId;

        @Schema(description = "사이즈")
        private String productDetSize;

        @Schema(description = "색상")
        private String productDetColor;

        @Schema(description = "할인율")
        private Integer skuDiscountRate;

        @Schema(description = "수량")
        private Integer quantity;

        @Schema(description = "단가")
        private BigDecimal unitPrice;

        @Schema(description = "소계 (단가 × 수량)")
        private BigDecimal subtotal;

        @Schema(description = "장바구니 담은 일시")
        private LocalDateTime creTm;
    }
}
