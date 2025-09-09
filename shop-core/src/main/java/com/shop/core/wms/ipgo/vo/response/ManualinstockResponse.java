package com.shop.core.wms.ipgo.vo.response;

import com.shop.core.interfaces.PageBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import com.shop.core.interfaces.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기타 입하 응답(Response) VO 클래스
 * 클라이언트로 반환하는 기타 입하 관련 응답 데이터를 정의
 */
public class ManualinstockResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ManualinstockResponsePaging", description = "기타 입하 페이징 응답", type = "object")
    public static class Paging extends PageBaseEntity {

        @Schema(description = "입하 ID")
        private Integer id;

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "파트너명")
        private String partnerNm;

        @Schema(description = "화주 ID")
        private Integer partnerId;

        @Schema(description = "발주상세 ID")
        private Integer asnDetId;

        @Schema(description = "입하 순서")
        private Integer stockSeq;

        @Schema(description = "입하 구분 코드")
        private String stockCd;

        @Schema(description = "제작 여부")
        private String prodAttrCd;

        @Schema(description = "입하일")
        private LocalDate stockYmd;

        @Schema(description = "입하 수량")
        private Integer stockCnt;

        @Schema(description = "존 정보")
        private String zone;

        @Schema(description = "입하 상태 코드")
        private String stockStatCd;

        @Schema(description = "입하 상태명")
        private String stockStatCdNm;

        @Schema(description = "입하 사유명")
        private String stockRsnCdNm;

        @Schema(description = "입하 사유코드")
        private String stockRsnCd;

        @Schema(description = "SKU명")
        private String skuNm;

        @Schema(description = "SKU 컬러")
        private String skuColor;

        @Schema(description = "SKU 사이즈")
        private String skuSize;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "공장ID(FK)")
        private Integer factoryId;

        @Schema(description = "수정자 ID")
        private String updUser;

        @Schema(description = "수정자명")
        private String updNm;



        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "수정일시")
        private LocalDateTime updTm;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockPartnerResponse", description = "화주 검색 결과", type = "object")
    public static class Partner {
        @Schema(description = "화주 ID")
        private Integer id;

        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "사업자번호")
        private String compNo;

        @Schema(description = "연락처")
        private String partnerTelNo;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockSkuResponse", description = "SKU 검색 결과", type = "object")
    public static class Sku {
        @Schema(description = "SKU ID")
        private Integer id;

        @Schema(description = "SKU 코드")
        private String skuCd;

        @Schema(description = "SKU명")
        private String skuNm;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "사이즈")
        private String skuSize;

        @Schema(description = "컬러")
        private String skuColor;

        @Schema(description = "")
        private String factoryNm;

        @Schema(description = "")
        private Integer factoryId;

        @Schema(description = "현재고")
        private Integer currentStock;

        @Schema(description = "DC/TC 구분")
        private String prodAttrCd;

        @Schema(description = "TC+상품명")
        private String prodAttrAndProdNm;

        @Schema(description = "발주 여부")
        private String hasAsn;

        @Schema(description = "총 발주 수량")
        private Integer totalAsnCnt;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockFactoryResponse", description = "공장 검색 결과", type = "object")
    public static class Factory {
        @Schema(description = "공장 ID")
        private Integer id;

        @Schema(description = "공장 구분 코드")
        private String factoryCd;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "공장/업체명")
        private String compNm;
    }

    @Getter
    @Setter
    @Schema(name = "ManualinstockAsnInfo", description = "ASN 정보", type = "object")
    public static class AsnInfo {
        @Schema(description = "ASN ID")
        private Integer id;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "발주 수량")
        private Integer genCnt;

        @Schema(description = "ASN 상태 코드")
        private String asnStatCd;

        @Schema(description = "입하 수량")
        private Integer stockCnt;
    }


}