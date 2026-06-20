package com.shop.core.biz.orderMng.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class OrderMngRequest {

    @Getter
    @Setter
    @Schema(name = "OrderMngRequestListFilter", description = "BO 주문 목록 조회 요청", type = "object")
    public static class ListFilter {

        @Schema(description = "조회 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fromDate;

        @Schema(description = "조회 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate toDate;
    }
}
