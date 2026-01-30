package com.shop.core.product.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(name = "SummaryRequest", description = "요약 관련 요청")
public class ProductMngRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "SummaryRequestForToday", description = "금일내역을 위한 요청", type = "object")
    public static class ForToday {

        @Schema(description = "파트너ID(FK)")
        private Integer partnerId;

        @Schema(description = "일자")
        private LocalDate workYmd;
    }
}
