package com.shop.core.wms.info.vo.response;

import com.shop.core.entity.LbProd;
import com.shop.core.entity.LbVersionSeller;
import com.shop.core.interfaces.PageBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 라방상품응답 응답 클래스
 */
public class LbProdResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "lbProdResponsePaging", description = "라방상품정보 페이징 응답", type = "object")
    public static class Paging extends PageBaseEntity {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "라방 상품 ID")
        private String prodId;

        @Schema(description = "라방 version")
        private String lbVersion;

        @Schema(description = "라방 파트너 ID")
        private String lbPartnerId;

        @Schema(description = "파트너명")
        private String lbPartnerNm;

        @Schema(description = "셀러명")
        private String lbSellerNm;

        @Schema(description = "라방타입")
        private String lbType;

        @Schema(description = "라방 구분")
        private String lbGubun;

        @Schema(description = "원상품명")
        private String prodNm;

        @Schema(description = "색상")
        private String color;

        @Schema(description = "색상")
        private String engColor;

        @Schema(description = "사이즈")
        private String size;

        @Schema(description = "SKU 명")
        private String skuNm;

        @Schema(description = "전시 SKU 명")
        private String dispSkuNm;

        @Schema(description = "도매가격")
        private Integer domaeAmt;

        @Schema(description = "판매가격")
        private Integer sellAmt;

        @Schema(description = "최소판매가격")
        private Integer minSellAmt;

        @Schema(description = "판매수량")
        private Integer sellCnt;

        @Schema(description = "총판매가격")
        private Integer totSellAmt;

        @Schema(description = "수량")
        private Integer skuCnt;

        @Schema(description = "lbSellingId")
        private Integer lbSellingId;

        @Schema(description = " 비고")
        private String sellingEtc;

        @Schema(description = " 비고")
        private String etc;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "lbProdResponseList", description = "라방상품정보 응답", type = "object")
    public static class LbProdList extends LbProd {
        @Schema(description = "파트너명")
        private String lbPartnerNm;

        @Schema(description = "셀러명")
        private String lbSellerNm;

        @Schema(description = "전시 SKU 명")
        private String dispSkuNm;

        @Schema(description = "판매수량")
        private Integer sellCnt;

        @Schema(description = "총판매가격")
        private Integer totSellAmt;

        @Schema(description = "남은건수")
        private Integer remainCnt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "lbProdForLableResponseList", description = "라방상품정보 응답", type = "object")
    public static class LbProdListForLabel {

        @Schema(description = "파트너명")
        private String lbPartnerNm;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "상품id")
        private String prodId;

        @Schema(description = "크기")
        private String size;

        @Schema(description = "컬러명")
        private String color;

        @Schema(description = "판매가격")
        private String sellAmt;

        @Schema(description = "최소판매가격")
        private String minSellAmt;

        @Schema(description = "라방구분")
        private String lbGubun;

    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "LbVersionSellerMapResponseList", description = "버전셀러매핑 응답", type = "object")
    public static class LbVersionSellerMapList extends LbVersionSeller{

        @Schema(description = "no")
        private Integer no;

        @Schema(description = "셀러명")
        private String lbSellerNm;

        @Schema(description = "lbVersionSellerId")
        private Integer lbVersionSellerId;

    }

}