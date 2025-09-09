package com.shop.core.biz.common.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PrintResponse {

    @Getter
    @Setter
    @Schema(name = "PrintDetail", description = "전표 디테일", type = "object")
    public static class PrintDetail {

        @Schema(description = "주문 ID")
        private Integer orderId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "전표")
        private String chitNo;

        @Schema(description = "판매처 ID")
        private Integer sellerId;

        @Schema(description = "판매처명")
        private String sellerNm;

        @Schema(description = "입력일자")
        private LocalDateTime inputDate;

        @Schema(description = "영업일")
        private LocalDate workYmd;

        @Schema(description = "장기건수")
        private Integer jangGgiCnt;

        @Schema(description = "입금 유형")
        private String payType;

        @Schema(description = "전잔액")
        private Integer previousBalance;

        @Schema(description = "현잔액")
        private Integer currentBalance;

        @Schema(description = "총 SKU 수량")
        private Integer totSkuCnt;

        @Schema(description = "총 주문 금액")
        private Integer totOrderAmt;

        @Schema(description = "인쇄 여부")
        private String etcPrintYn;

        @Schema(description = "주문비고")
        private String orderEtc;

        @Schema(description = "주문 상세 항목")
        private List<PrintResponse.PrintItem> OrderItems;
    }

    @Getter
    @Setter
    @Schema(name = "PrintItem", description = "전표 디테일", type = "object")
    public static class PrintItem {

        @Schema(description = "ID")
        private Integer id;

        @Schema(description = "상품명")
        private String skuNm;

        @Schema(description = "단가")
        private BigDecimal unitPrice;

        @Schema(description = "수량")
        private Integer quantity;

        @Schema(description = "금액")
        private BigDecimal amount;

        @Schema(description = "코드")
        private Integer orderDetCd;

    }
}