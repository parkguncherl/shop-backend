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

        @Schema(description = "주문상태 단일 필터 (없으면 O,P,R,S,D 전체)")
        private String orderStatus;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "MisRequestSalesStatFilter", description = "MIS 판매 실적 조회 필터", type = "object")
    public static class SalesStatFilter {

        @Schema(description = "조회 유형 (monthly | weekly)")
        private String viewType;

        @Schema(description = "조회 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fromDate;

        @Schema(description = "조회 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate toDate;

        @Schema(description = "주문상태 단일 필터 (없으면 O,P,R,S,D 전체)")
        private String orderStatus;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "MisRequestCategoryViewFilter", description = "MIS 카테고리 분석 조회 필터", type = "object")
    public static class CategoryViewFilter {

        @Schema(description = "조회 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fromDate;

        @Schema(description = "조회 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate toDate;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "MisRequestSalesStatDetailFilter", description = "MIS 판매 실적 상세 조회 필터", type = "object")
    public static class SalesStatDetailFilter {

        @Schema(description = "조회 유형 (monthly | weekly)")
        private String viewType;

        @Schema(description = "기간 값 (월별: 2024-01, 주차별: 2024-W03)")
        private String period;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }
}
