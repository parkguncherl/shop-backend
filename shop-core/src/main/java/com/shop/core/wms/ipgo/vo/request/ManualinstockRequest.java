package com.shop.core.wms.ipgo.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 기타 입하 요청(Request) VO 클래스
 * 클라이언트로부터 받는 기타 입하 관련 요청 데이터를 정의
 */
public class ManualinstockRequest {

    @Getter
    @Setter
    @Schema(name = "ManualinstockPagingFilter", description = "기타 입하 정보 페이징 필터", type = "object")
    public static class PagingFilter {

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "파트너명")
        private String partnerNm;

        @Schema(description = "상품명 검색")
        private String prodNm;

        @Schema(description = "공장명 검색")
        private String compNm;

        @Schema(description = "SKU명 검색")
        private String skuNm;

        @Schema(description = "수정자명 검색")
        private String updNm;

        @Schema(description = "입하 상태 코드")
        private String inStockStatCd;

        @Schema(description = "검색 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate startDate;

        @Schema(description = "검색 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endDate;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "입하 상태 코드")
        private String stockStatCd;

        @Schema(description = "상품스큐 동시검색")
        private String searchSpNm;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockPartnerSearchFilter", description = "화주 검색 필터", type = "object")
    public static class PartnerSearchFilter {
        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "사업자번호")
        private String compNo;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockSkuSearchFilter", description = "SKU 검색 필터", type = "object")
    public static class SkuSearchFilter {
        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "공장 ID")
        private Integer factoryId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "SKU명")
        private String skuNm;

        @Schema(description = "DC/TC 구분")
        private String prodAttrCd;

        @Schema(description = "TC+상품명")
        private String prodAttrAndProdNm;

        @Schema(description = "상품스큐 동시검색")
        private String searchSpNm;
    }
    @Getter
    @Setter
    @Schema(name = "ManualinstockFactorySearchFilter", description = "공장 검색 필터", type = "object")
    public static class FactorySearchFilter {
        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "공장/업체명")
        private String compNm;
    }
    @Getter
    @Setter
    @Schema(name = "ManualinstockCreate", description = "기타 입하 생성 요청", type = "object")
    public static class Create {
        @Schema(description = "물류센터 ID")
        private Integer logisId;

        @Schema(description = "화주 ID")
        private Integer partnerId;

        @Schema(description = "입하 상세 목록")
//        private List<AsnDetailInfo> asnDetails;
        private AsnDetailInfo asnDetails;

        @Schema(description = "생성자")
        private String creUser;

        @Schema(description = "ASN ID")
        private Integer asnId;

        @Schema(description = "공장ID(FK)")
        private Integer factoryId;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockAsnDetailInfo", description = "입하 상세 정보", type = "object")
    public static class AsnDetailInfo {
        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "입하수량")
        private Integer stockCnt;

        @Schema(description = "Zone")
        private String zone;

        @Schema(description = "입고 사유 코드")
        private String stockRsnCd;

        @Schema(description = "공장ID(FK)")
        private Integer factoryId;

        @Schema(description = "asnID(FK)")
        private Integer asnId;
    }
}
