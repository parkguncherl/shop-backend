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
        private Long purchaseCnt;
        private Long cartCnt;
        private Long pageViewCnt;
        private BigDecimal totalScore;
    }
}
