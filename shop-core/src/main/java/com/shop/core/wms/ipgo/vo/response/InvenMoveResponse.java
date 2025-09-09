package com.shop.core.wms.ipgo.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 재고이동 응답 클래스
 */
public class InvenMoveResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InventoryMoveResponsePaging", description = "재고이동 목록 페이징 응답", type = "object")
    public static class Paging {

        @Schema(description = "전체 행 수")
        private Long totalRowCount;

        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "SKU_ID(FK)")
        private Long skuId;

        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "SKU 컬러")
        private String skuColor;

        @Schema(description = "SKU 사이즈")
        private String skuSize;

        @Schema(description = "재고수량")
        private Integer invenCnt;

        @Schema(description = "구역명")
        private String zoneCdNm;

        @Schema(description = "위치명")
        private String locNm;

        @Schema(description = "위치ID")
        private int locId;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class InvenCount {

        @Schema(description = "SKU_ID(FK)")
        private Long skuId;

        @Schema(description = "위치ID")
        private int locId;

        @Schema(description = "재고수량")
        private Integer invenCnt;
    }
}