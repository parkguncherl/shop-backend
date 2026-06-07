package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CartRequest {

    @Getter
    @Setter
    @Schema(name = "CartRequestAddItem", description = "장바구니 상품 추가 요청", type = "object")
    public static class AddItem {

        @Schema(description = "게스트 ID", required = true)
        private String guestId;

        @Schema(description = "상품 ID (tb_product.id)", required = true)
        private Long productId;

        @Schema(description = "상품 상세 ID (tb_product_det.id)")
        private Long productDetId;

        @Schema(description = "수량", required = true)
        private Integer quantity;

        @Schema(description = "단가 (담을 당시 가격)")
        private BigDecimal unitPrice;

        @Schema(description = "옵션 스냅샷 JSON 문자열")
        private String optionsSnapshot;
    }

    @Getter
    @Setter
    @Schema(name = "CartRequestUpdateItem", description = "장바구니 수량 수정 요청", type = "object")
    public static class UpdateItem {

        @Schema(description = "장바구니 아이템 ID", required = true)
        private Long cartItemId;

        @Schema(description = "수량", required = true)
        private Integer quantity;
    }

    @Getter
    @Setter
    @Schema(name = "CartRequestGetCart", description = "장바구니 조회 요청", type = "object")
    public static class GetCart {

        @Schema(description = "게스트 ID")
        private String guestId;

        @Schema(description = "회원 ID (로그인 시)")
        private Integer memberId;
    }
}
