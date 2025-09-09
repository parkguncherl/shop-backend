package com.shop.core.wms.ipgo.vo.response;

import com.shop.core.entity.Stock;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 입하정보 응답 클래스
 */
public class InstockResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockResponsePaging", description = "입하정보 페이징 응답", type = "object")
    public static class Paging {

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

        @Schema(description = "고객명")
        private String partnerNm;

        @Schema(description = "생산처")
        private String factoryNm;

        @Schema(description = "발주일자")
        private LocalDate asnWorkYmd;

        @Schema(description = "입고예정일")
        private LocalDate outYmd;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

        @Schema(description = "SKU종류 수량")
        private Integer skuCnt;

        @Schema(description = "총 발주수량")
        private Integer totalGenCnt;

        @Schema(description = "기 입하완료 수량")
        private Integer befAsnCompleteCnt;

        @Schema(description = "전표인쇄작업자")
        private String sheetPrintUser;

        @Schema(description = "전표인쇄시간")
        private LocalDateTime sheetPrintDt;

        @Schema(description = "입하상태명")
        private String asnStatNm;

        @Schema(description = "입하추가명")
        private String repAsnNm;

        @Schema(description = "대리입하여부")
        private String repYn;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockReturnResponsePaging", description = "입하정보(매장분반납) 페이징 응답", type = "object")
    public static class ReturnPaging {

        @Schema(description = "전체 행 수")
        private Long totalRowCount;

        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "고객ID")
        private Integer partnerId;

        @Schema(description = "고객사")
        private String partnerNm;

        @Schema(description = "고객사")
        private String repNm;

        @Schema(description = "입고예정일")
        private LocalDate outYmd;

        @Schema(description = "발주일자")
        private LocalDate workYmd;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

        @Schema(description = "SKU종류 수량")
        private Integer skuCnt;

        @Schema(description = "총 발주수량")
        private Integer totalGenCnt;

        @Schema(description = "기 입하완료 수량")
        private Integer befAsnCompleteCnt;

        @Schema(description = "전표인쇄시간")
        private LocalDateTime sheetPrintDt;

        @Schema(description = "전표인쇄작업자")
        private String sheetPrintNm;

        @Schema(description = "입하상태명")
        private String asnStatNm;

        @Schema(description = "입하추가명")
        private String repAsnNm;

        @Schema(description = "반납확정일시")
        private LocalDateTime creTm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockResponseStatDashBoard", description = "입하정보 대시보드 응답", type = "object")
    public static class StatDashBoard {
        @Schema(description = "발주 총 입하수량(예정)")
        private Integer totalFactoryInstock;

        @Schema(description = "발주 입하처리수량(완료)")
        private Integer pastFactoryInstockedCnt;

        @Schema(description = "수선 총 입하수량(예정)")
        private Integer totalOutgoingInstock;

        @Schema(description = "수선 입하처리수량(완료)")
        private Integer pastOutgingInstockedCnt;

        @Schema(description = "매장반납 총 입하수량(예정)")
        private Integer totalStoreReqInstock;

        @Schema(description = "매장반납 입하처리수량(완료)")
        private Integer pastStoreReqInstockedCnt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockResponsePrintDetail", description = "입하 전표 상세 응답")
    public static class PrintDetail {

        @Schema(description = "상세아이템 목록")
        private List<InstockResponse.DetailItem> detailItems;

        @Schema(description = "작업상태")
        private String jobStat;

        @Schema(description = "고객사명")
        private String partnerNm;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "발주일")
        private String workYmd;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "수량합계")
        private Integer totCnt;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

        @Schema(description = "전표출력자")
        private String printUser;

        @Schema(description = "입하추가구분 정보(발주입하/기타입하)")
        private String repAsn;

        @Schema(description = "반납시간")
        private String creTm;
    }

    @Getter
    @Setter
    @Schema(name = "InstockResponseDetailItem", description = "입하 전표 상세품목 응답")
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
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockResponseInstockFactoryAsnDetail", description = "입하처리(생산처발주) 상세내역 응답", type = "object")
    public static class InstockFactoryAsnDetail {
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

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "입하구분")
        private String stockCd;

        @Schema(description = "TC상품여부")
        private String prodAttrCd;

        @Schema(description = "상품명")
        private String skuNm;

        @NotNull
        @Schema(description = "발주수량")
        private Integer genCnt;

        @NotNull
        @Schema(description = "입하수량")
        private Integer asnCnt;

        @NotNull
        @Schema(description = "입고수량")
        private Integer stockCnt;

        @NotNull
        @Schema(description = "입고수량(비교용)")
        private Integer orgStockCnt;

        @Schema(description = "입하상태")
        private String asnStatNm;

        @Schema(description = "입하작업자")
        private String stockUser;

        @Schema(description = "입하작업일시")
        private LocalDateTime stockTm;

        @Schema(description = "대리입하유무")
        private String repYn;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InstockReturnResponseDetail", description = "입하처리(매장분반납) 상세내역 응답", type = "object")
    public static class InstockReturnDetail {
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

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class StockInfo extends Stock {
        private String skuNm;
    }
}