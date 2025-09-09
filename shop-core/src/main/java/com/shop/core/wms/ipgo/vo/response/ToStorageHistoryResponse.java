package com.shop.core.wms.ipgo.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 적치이력 응답 클래스
 */
public class ToStorageHistoryResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ToStorageHistoryResponsePaging", description = "적치이력 페이징 응답", type = "object")
    public static class Paging {

        @Schema(description = "전체 행 수")
        private Long totalRowCount;

        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "입고ID")
        private Integer stockId;

        @Schema(description = "로케이션ID")
        private Integer locId;

        @Schema(description = "물류ID")
        private Integer logisId;

        @Schema(description = "고객사ID")
        private Integer partnerId;

        @Schema(description = "고객사명")
        private String partnerNm;

        @Schema(description = "생산처")
        private String factoryNm;

        @Schema(description = "발주유형")
        private String asnTypeNm;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "적치일시")
        private LocalDateTime storageDtm;

        @Schema(description = "TC유무")
        private String prodTcYn;

        @Schema(description = "작업자")
        private String workUser;

        @Schema(description = "ZONE명")
        private String zoneNm;

        @Schema(description = "로케이션명")
        private String locNm;

        @Schema(description = "재고수량")
        private Integer invenCnt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ToStorageResponseLocInfo", description = "해당 상품 적치 정보", type = "object")
    public static class ProdLocInfo {

        @Schema(description = "ZONE명")
        private String zoneNm;

        @Schema(description = "로케이션명")
        private String locNm;

        @Schema(description = "상품명")
        private String skuNm;

        @Schema(description = "적치수량")
        private Integer invenCnt;

        @Schema(description = "대상 SKU 유무")
        private String targetSkuYn;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ToStorageResponsePrintDetail", description = "적치 전표 상세 응답")
    public static class PrintDetail {

        @Schema(description = "상세아이템 목록")
        private List<ToStorageHistoryResponse.DetailItem> detailItems;

        @Schema(description = "작업상태")
        private String jobStat;

        @Schema(description = "고객사명")
        private String partnerNm;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "입고일")
        private String workYmd;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "수량합계")
        private Integer totCnt;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;
    }

    @Getter
    @Setter
    @Schema(name = "ToStorageResponseDetailItem", description = "입하 전표 상세품목 응답")
    public static class DetailItem {

        @Schema(description = "발주 ID")
        private Integer asnId;

        @Schema(description = "품목명")
        private String skuNm;

        @Schema(description = "수량")
        private Integer quantity;
    }

    @Getter
    @Setter
    @Schema(name = "ToStorageResponseStockInfo", description = "입고정보 응답")
    public static class StockInfo {

        @Schema(description = "입고 ID")
        private Integer stockId;

        @Schema(description = "입하완료수량")
        private Integer stockCnt;

        @Schema(description = "입고상태코드")
        private String stockStatCd;
    }

}