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

    @Getter
    @Setter
    @Schema(name = "MisResponseDailySalesStat", description = "MIS 금일/어제 판매 현황", type = "object")
    public static class DailySalesStat {
        private Long todayPurchaseCnt;
        private Long todayPaymentAmt;
        private Long yesterdayPurchaseCnt;
        private Long yesterdayPaymentAmt;
        private Long todayCancelAmt;
        private Long yesterdayCancelAmt;
    }

    @Getter
    @Setter
    @Schema(name = "MisResponseCategoryViewItem", description = "MIS 카테고리 분석 항목", type = "object")
    public static class CategoryViewItem {
        private Integer categoryId;
        private String categoryNm;
        private Long totalPaymentAmt;
        private Long purchaseCnt;
        private Long cartCnt;
        private Long pageViewCnt;
        private BigDecimal totalScore;
    }
}
