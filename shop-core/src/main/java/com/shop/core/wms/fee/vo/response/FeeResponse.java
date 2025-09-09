package com.shop.core.wms.fee.vo.response;

import com.shop.core.entity.PartnerFee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 적하정보 응답 클래스
 */
public class FeeResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "partnerFeeResponse", description = "수수료 응답", type = "object")
    public static class PartnerFeeResponse extends PartnerFee {

        @Schema(description = "no")
        private Integer no;

        @Schema(description = "수수료명")
        private String feeTypeNm;

        @Schema(description = "파트너명")
        private String partnerNm;

        @Schema(description = "적용 일")
        private String createDayFormated;

        @Schema(description = "적용 시작일")
        private String startDayFormated;

    }

}