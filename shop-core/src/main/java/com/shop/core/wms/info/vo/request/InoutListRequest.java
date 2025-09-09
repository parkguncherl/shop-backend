package com.shop.core.wms.info.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 적치정보 요청 클래스
 */
public class InoutListRequest {

    @Getter
    @Setter
    @Schema(name = "inoutListRequest", description = "기간별 입출고 필터")
    public static class InoutListFilter {

        @Schema(description = "시작일자")
        private LocalDate startDate;

        @Schema(description = "종료일자")
        private LocalDate endDate;

        @Schema(description = "뮬류센터id")
        private Integer logisId;

        @Schema(description = "화주ID(FK)")
        private Integer partnerId;

        @Schema(description = "일별, 주별, 월별")
        private String searchType;

        @Schema(description = "제작/일반구분")
        private String prodAttrCd;

        @Schema(description = "상품명")
        private String skuNm;

        @Schema(description = "feeType")
        private String feeType;

        @Schema(description = "입고수수료 건당")
        private BigDecimal stockFee;

        @Schema(description = "출고수수료 건당")
        private BigDecimal jobFee;

        @Schema(description = "일반요율 % ")
        private BigDecimal serviceFee;

        @Schema(description = "제작요율 % ")
        private BigDecimal orderFee;

        @Schema(description = "보관수수료 건당")
        private BigDecimal maintFee;

        @Schema(description = "보관수수료 행거 건당")
        private BigDecimal hangerFee;
    }
}