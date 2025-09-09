package com.shop.core.wms.info.vo.request;

import com.shop.core.wms.info.vo.response.ipgologResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 적치정보 요청 클래스
 */
public class ipgologRequest {

    @Getter
    @Setter
    @Schema(name = "ipgologePagingFilter", description = "적치정보 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "상품명(SKU) 검색")
        private String skuNm;

        @Schema(description = "창고Id")
        private Integer logisId;

        @Schema(description = "화주ID")
        private int partnerId;

        @Schema(description = "공장ID")
        private int factoryId;

        @Schema(description = "시작일")
        private LocalDate startDate;

        @Schema(description = "종료일")
        private LocalDate endDate;

        @Schema(description = "적치상태코드")
        private String stockStatCd;
    }
}