package com.shop.core.wms.ipgo.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 입하이력정보 응답 클래스
 */
public class InstockHistoryResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockHistoryResponseFactoryPaging", description = "입하이력정보 페이징 응답", type = "object")
    public static class FactoryPaging {

        @Schema(description = "전체 행 수")
        private Long totalRowCount;

        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "고객ID")
        private Integer partnerId;

        @Schema(description = "생산처ID")
        private Integer factoryId;

        @Schema(description = "발주유형코드")
        private String asnTypeCd;

        @Schema(description = "발주유형코드명")
        private String asnTypeNm;

        @Schema(description = "고객명")
        private String partnerNm;

        @Schema(description = "생산처")
        private String factoryNm;

        @Schema(description = "발주일자")
        private LocalDate asnWorkYmd;

        @Schema(description = "입고예정일")
        private LocalDate outYmd;

        @Schema(description = "총 입하완료 수량")
        private Integer totalCompleteInstock;

        @Schema(description = "총 입하취소 수량")
        private Integer totalCancelInStock;

        @Schema(description = "작업자")
        private String instockUser;

        @Schema(description = "작업일시")
        private LocalDateTime instockDtm;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockHistoryResponseReturnPaging", description = "입하정보(매장분반납) 페이징 응답", type = "object")
    public static class ReturnPaging {

        @Schema(description = "전체 행 수")
        private Long totalRowCount;

        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "고객ID")
        private Integer partnerId;

        @Schema(description = "고객사")
        private String partnerNm;

        @Schema(description = "고객명(발주확정자)")
        private String returnUser;

        @Schema(description = "총 입하완료 수량")
        private Integer totalCompleteInstock;

        @Schema(description = "총 입하취소 수량")
        private Integer totalCancelInStock;

        @Schema(description = "반납 변동수량")
        private Integer totalDiffReturnCnt;

        @Schema(description = "입하작업자")
        private String instockUser;

        @Schema(description = "입하작업일시")
        private LocalDateTime instockDtm;

        @Schema(description = "반납확정일시")
        private LocalDateTime returnDtm;

        @Schema(description = "발주유형코드")
        private String asnTypeCd;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockHistoryResponsePrintDetail", description = "입하 전표 상세 응답")
    public static class PrintDetail {

        @Schema(description = "상세아이템 목록")
        private List<DetailItem> detailItems;

        @Schema(description = "작업상태")
        private String jobStat;

        @Schema(description = "고객사명")
        private String partnerNm;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "발주일")
        private String workYmd;

        @Schema(description = "반납시간")
        private String creTm;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "수량합계")
        private Integer totCnt;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

        @Schema(description = "입하작업자")
        private String printUser;
    }

    @Getter
    @Setter
    @Schema(name = "InstockHistoryResponseDetailItem", description = "입하 전표 상세품목 응답")
    public static class DetailItem {

        @Schema(description = "발주 ID")
        private Integer asnId;

        @Schema(description = "품목명")
        private String skuNm;

        @Schema(description = "수량")
        private Integer quantity;

        @Schema(description = "비고내용")
        private String etcCntn;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockHistoryResponseInstockHistoryFactoryDetail", description = "발주 입하이력 상세내역 응답", type = "object")
    public static class InstockHistoryFactoryDetail {
        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "ASN ID")
        private Integer asnId;

        @Schema(description = "화주 ID")
        private Integer partnerId;

        @Schema(description = "생산처 ID")
        private Integer factoryId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "상품명")
        private String skuNm;

        @Schema(description = "발주일")
        private LocalDate workYmd;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "대리입하정보")
        private String repAsn;

        @NotNull
        @Schema(description = "입하완료수량")
        private Integer stockCnt;

        @NotNull
        @Schema(description = "입하취소수량")
        private Integer cancelInstockCnt;

        @Schema(description = "작업자")
        private String stockUser;

        @Schema(description = "작업일시")
        private LocalDateTime stockDtm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockHistoryResponseInstockHistoryReturnDetail", description = "입하이력(매장분반납) 상세내역 응답", type = "object")
    public static class InstockHistoryReturnDetail {
        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "ASN ID")
        private Integer asnId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "화주 ID")
        private Integer partnerId;

        @Schema(description = "입하구분코드")
        private String stockCd;

        @Schema(description = "TC상품여부")
        private String prodAttrCd;

        @Schema(description = "상품명")
        private String skuNm;

        @Schema(description = "입하상태")
        private String asnStatNm;

        @Schema(description = "매장재고")
        private Integer partnerInventoryAmt;

        @Schema(description = "반납확정")
        private Integer genCnt;

        @Schema(description = "입하수량")
        private Integer asnCnt;

        @Schema(description = "입고수량")
        private Integer stockCnt;

        @Schema(description = "입하수량 복사")
        private Integer orgStockCnt;

        @Schema(description = "입하일시")
        private LocalDateTime creTm;

        @Schema(description = "입하작업자")
        private String stockUser;
    }
}