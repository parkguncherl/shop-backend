package com.shop.core.biz.mis.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class MisRequest {

    @Getter
    @Setter
    @Schema(name = "MisRequestListFilter", description = "MIS 상품 분석 조회 필터", type = "object")
    public static class ListFilter {

        @Schema(description = "조회 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fromDate;

        @Schema(description = "조회 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate toDate;

        @Schema(description = "계절 필터 (spring, summer, autumn, winter)")
        private List<String> weather;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }
}
