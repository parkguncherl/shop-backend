package com.shop.core.wms.ipgo.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * WMS 발주 요청 클래스
 */
public class AsnRequest {

    @Getter
    @Setter
    @Schema(name = "AsnRequestPagingFilter", description = "WMS 발주 목록 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "물류_ID")
        private Integer logisId;

        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @Schema(description = "상품 검색")
        private String prodNm;

        @Schema(description = "생산처 검색")
        private String compNm;

        @Schema(description = "발주유형")
        private String asnType;

//        @Schema(description = "검색 시작일")
//        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//        private LocalDate startDate;
//
//        @Schema(description = "검색 종료일")
//        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//        private LocalDate endDate;
    }

    @Getter
    @Setter
    @Schema(name = "AsnRequestStatDashBoard", description = "WMS 발주 목록 통계 대시보드 요청")
    public static class StatDashBoard {
        @Schema(description = "물류_ID")
        private Integer logisId;
    }

    @Getter
    @Setter
    @Schema(name = "AsnRequestDetail", description = "WMS 발주 상세 요청")
    public static class Detail {

        @NotNull
        @Schema(description = "물류_ID")
        private Integer logisId;

        @NotNull
        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "생산처_ID")
        private Integer factoryId;

        @NotNull
        @Schema(description = "발주구분")
        private String asnType;

        @NotNull
        @Schema(description = "발주일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate workYmd;
    }

    @Getter
    @Setter
    @Schema(name = "AsnRequestUpdateAsn", description = "WMS 발주 입하등록 추가 요청")
    public static class UpdateAsn {
        @NotNull
        @Schema(description = "발주 아이디")
        private Integer asnId;

        @JsonIgnore
        @Schema(description = "입하예정 수량")
        private Integer asnCnt;

        @JsonIgnore
        @Schema(description = "수정자")
        private String updUser;

        @JsonIgnore
        @Schema(description = "발주상태코드")
        private String asnStatCd;

        @JsonIgnore
        @Schema(description = "대리입하유무")
        private String repYn;
    }


    @Getter
    @Setter
    @Schema(name = "AsnRequestCreateAsn", description = "WMS 발주 입하등록 요청")
    public static class CreateAsn {
        @NotNull
        @Schema(description = "발주 아이디")
        private Integer partnerId;

        @Schema(description = "발주상태코드")
        private String asnStatCd;

        @Schema(description = "기존 매장분 입하 만들어진 시간 매장분입하때문에 만들어진 시간")
        private String createDateTime;

        @Schema(description = "기존 매장분 반납 발주날짜")
        private String workYmd;

        @Schema(description = "입하예정 수량")
        List<AsnUnit> asnUnits;
    }
    
    @Getter
    @Setter
    @Schema(name = "AsnUnit", description = "WMS 발주 입하등록 asn unit")
    public static class AsnUnit {
        @NotNull
        @Schema(description = "sku 아이디")
        private Integer skuId;

        @NotNull
        @Schema(description = "수량")
        private Integer skuCnt;
    }


}