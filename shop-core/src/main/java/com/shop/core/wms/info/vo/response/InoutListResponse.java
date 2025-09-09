package com.shop.core.wms.info.vo.response;

import com.shop.core.interfaces.PageBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 적하정보 응답 클래스
 */
public class InoutListResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "inoutListResponse", description = "입하정보 페이징 응답", type = "object")
    public static class InoutList {

        @Schema(description = "no")
        private Integer no;

        @Schema(description = "작업일자")
        private String workYmd;

        @Schema(description = "파트너명")
        private String partnerNm;

        @Schema(description = "시작 재고 수량")
        private Integer startInvenCnt;

        @Schema(description = "적치완료 수량")
        private Integer stockCnt;

        @Schema(description = "적치 수수료 금액")
        private BigDecimal stockFeeAmt;

        @Schema(description = "보관 금액")
        private BigDecimal maintFeeAmt;

        @Schema(description = "작업 건수")
        private Integer jobCnt;

        @Schema(description = "작업 수수료 금액")
        private BigDecimal jobFeeAmt;

        @Schema(description = "작업 총 금액")
        private BigDecimal jobTotAmt;

        @Schema(description = "서비스 금액 일반건")
        private BigDecimal serviceFeeAmt;

        @Schema(description = "서비스금액 오다건")
        private BigDecimal orderFeeAmt;

        @Schema(description = "총수수료")
        private BigDecimal totalFeeAmt;

        @Schema(description = "재고 증가 수량")
        private Integer invenIncCnt;

        @Schema(description = "종료 재고 수량")
        private Integer endInvenCnt;

        @Schema(description = "종료 재고 수수료 금액")
        private BigDecimal endInvenFeeAmt;

        @Schema(description = "행거 수량")
        private Integer hangerCnt;

        @Schema(description = "행거 수수료 금액")
        private BigDecimal hangerFeeAmt;

        @Schema(description = "ASN 입하 수량 (공장입하 + 기타입하)")
        private Integer asnStockCnt;

        @Schema(description = "수선발주 수량")
        private Integer repairStockCnt;

        @Schema(description = "사은품 수량")
        private Integer giftStockCnt;

        @Schema(description = "판매 주문 수량")
        private Integer sailCnt;

        @Schema(description = "미송 수량")
        private Integer misongCnt;

        @Schema(description = "샘플 수량")
        private Integer sampleCnt;

        @Schema(description = "매장 수량")
        private Integer maejangCnt;

        @Schema(description = "기타 수량")
        private Integer etcCnt;

    }
}