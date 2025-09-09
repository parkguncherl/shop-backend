package com.shop.core.wms.ipgo.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * 적치등록 요청 클래스
 */
public class ToStorageRequest {

    @Getter
    @Setter
    @Schema(name = "ToStoragePagingFilter", description = "적치등록 목록 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "물류id")
        private Integer logisId;

        @Schema(description = "고객사ID")
        private Integer partnerId;

        @Schema(description = "공장ID")
        private Integer factoryId;

        @Schema(description = "시작일")
        private LocalDate startDate;

        @Schema(description = "종료일")
        private LocalDate endDate;

        @Schema(description = "적치LCTN 구분값 코드 (코드 : 전체:all, 없음: none, 단일: single, 복수: multi")
        private String lctnTypeCd;

        @Schema(description = "출고지시 구분값 코드 (코드 : 전체:all, 출고없음: none, 출고부족: impossible, 출고가능: possible")
        private String pickingTypeCd;

        @Schema(description = "임시보관 ZONE ID")
        private Integer tszZoneId;

        @Schema(description = "제작TC ZONE ID")
        private Integer ptczZoneId;

        @Schema(description = "수선TC ZONE ID")
        private Integer rtczZoneId;

        @Schema(description = "임시보관 로케이션 ID")
        private Integer tszLocId;

        @Schema(description = "제작TC 로케이션 ID")
        private Integer ptczLocId;

        @Schema(description = "수선TC 로케이션 ID")
        private Integer rtczLocId;

        @JsonIgnore
        @Schema(description = "발주구분 리스트")
        private List<String> asnTypeList;
    }

    @Getter
    @Setter
    @Schema(name = "ToStorageRequestProdLoc", description = "해당 상품 적치정보 요청 파라미터")
    public static class ProdLoc {

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "상품 ID")
        private Integer prodId;

    }

    @Getter
    @Setter
    @Schema(name = "ToStorageRequestPrintDetail", description = "적치 전표 요청 파라미터")
    public static class PrintDetail {

        @Schema(description = "발주ID")
        private Integer asnId;
    }

    @Getter
    @Setter
    @Schema(name = "ToStorageRequestCreate", description = "적치등록 요청 파라미터")
    public static class Create {

        @Schema(description = "입고ID")
        private Integer stockId;

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @Schema(description = "로케이션 ID")
        private Integer locId;

        @Schema(description = "스큐ID")
        private Integer skuId;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "입하수량")
        private Integer stockCnt;

        @Schema(description = "적치입력수량")
        private Integer storageCnt;
    }
}