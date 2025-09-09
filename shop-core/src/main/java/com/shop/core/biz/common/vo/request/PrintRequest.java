package com.shop.core.biz.common.vo.request;

import com.shop.core.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PrintRequest")
public class PrintRequest {
    @Getter
    @Setter
    @Schema(name = "PrintOrderDetail", description = "주문등록 프린트 요청")
    public static class PrintOrderDetail extends Order {

        @Schema(description = "번호")
        private Integer no;

        @Schema(description = "판매가")
        private Integer baseAmt;

        @Schema(description = "할인 금액")
        private Integer dcAmt;

        @Schema(description = "주문 상세 코드")
        private String orderDetCd;

        @Schema(description = "파트너 재고 금액")
        private Integer partnerInventoryAmt;

        @Schema(description = "상품 ID")
        private Integer prodId;

        @Schema(description = "판매가")
        private Integer sellAmt;

        @Schema(description = "판매자 이름")
        private String sellerNm;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "SKU 코드")
        private String skuCd;

        @Schema(description = "SKU 수량")
        private Integer skuCnt;

        @Schema(description = "SKU 색상")
        private String skuColor;

        @Schema(description = "SKU 이름")
        private String skuNm;

        @Schema(description = "SKU 사이즈")
        private String skuSize;

        @Schema(description = "휴면여부")
        private String sleepYn;

        @Schema(description = "총 금액")
        private Integer totAmt;

    }

}