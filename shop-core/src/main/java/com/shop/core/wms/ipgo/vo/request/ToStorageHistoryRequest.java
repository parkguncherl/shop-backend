package com.shop.core.wms.ipgo.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 적치이력 요청 클래스
 */
public class ToStorageHistoryRequest {

    @Getter
    @Setter
    @Schema(name = "ToStorageHistoryRequestPagingFilter", description = "적치이력 목록 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "물류ID")
        private Integer logisId;

        @Schema(description = "고객사ID")
        private Integer partnerId;

        @Schema(description = "공장ID")
        private Integer factoryId;

        @Schema(description = "시작일")
        private LocalDate startDate;

        @Schema(description = "종료일")
        private LocalDate endDate;
    }


    @Getter
    @Setter
    @Schema(name = "ToStorageHistoryRequestCancel", description = "적치취소 요청 파라미터")
    public static class Cancel {

        @NotNull
        @Schema(description = "입고ID")
        private Integer stockId;

        @NotNull
        @Schema(description = "물류 ID")
        private Integer logisId;

        @NotNull
        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "스큐명")
        private String skuNm;
    }
}