package com.shop.core.biz.mis.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class MisResponse {

    @Getter
    @Setter
    @Schema(name = "MisResponseProductViewItem", description = "MIS 상품 분석 항목", type = "object")
    public static class ProductViewItem {
        private Integer prodId;
        private String prodNm;
        private Integer repFileId;
        private Long totalPaymentAmt;
        private Long purchaseCnt;
        private Long cartCnt;
        private Long pageViewCnt;
        private BigDecimal totalScore;
    }

    @Getter
    @Setter
    @Schema(name = "MisResponseSalesStatItem", description = "MIS 판매 실적 항목", type = "object")
    public static class SalesStatItem {
        private String period;
        private Long totalPaymentAmt;
        private Long purchaseCnt;
    }
}
