package com.shop.core.wms.ipgo.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 입하정보 요청 클래스
 */
public class InstockRequest {

    @Getter
    @Setter
    @Schema(name = "InstockPagingFilter", description = "입하 정보 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "창고id")
        private Integer logisId;

        @Schema(description = "화주ID")
        private int partnerId;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "발주구분")
        private String asnType;

        @JsonIgnore
        @Schema(description = "발주구분 리스트")
        private List<String> asnTypeList;
    }

    @Getter
    @Setter
    @Schema(name = "InstockRequestDashboard", description = "입하 정보 대시보드 요청")
    public static class DashBoard {

        @NotNull
        @Schema(description = "창고id")
        private Integer logisId;
   }

    @Getter
    @Setter
    @Schema(name = "InstockRequestDetail", description = "입하 정보 상세 요청")
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

        @NotNull
        @Schema(description = "입하추가구분명")
        private String repAsnNm;

        @JsonIgnore
        @Schema(description = "대리입하유무")
        private String repYn;

        @JsonIgnore
        @Schema(description = "상태")
        private String[] asnStatCds;
    }

    @Getter
    @Setter
    @Schema(name = "InstockReturnRequestDetail", description = "매장분 반납 입하처리 상세 내역 요청")
    public static class ReturnDetail {

        @NotNull
        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "입하일시")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime creTm;

    }


    @Getter
    @Setter
    @Schema(name = "InstockRequestCreate", description = "입하정보 생성 요청 파라미터")
    public static class Create {

        @Schema(description = "ASN ID")
        private Integer asnId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "상품종류코드")
        private String prodAttrCd;

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "물류 ID")
        private Integer partnerId;

        @Schema(description = "발주구분코드")
        private String asnType;

        @Schema(description = "입하예정수량")
        private Integer asnCnt;

        @Schema(description = "입하처리수량")
        private Integer stockCnt;

        @Schema(description = "입하구분코드")
        private String stockCd;

        @Schema(description = "입하작업자")
        private String stockUserLoginId;

        @Schema(description = "적치ID")
        private Integer stockId;

        @Schema(description = "즉시적치여부")
        private boolean immiInvenYn;

    }

    @Getter
    @Setter
    @Schema(name = "InstockRequestDelete", description = "입하정보 삭제 요청 파라미터")
    public static class Delete {

        @Schema(description = "물류 ID")
        private Integer logisId;

        @Schema(description = "물류 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "발주ID")
        private Integer asnId;

        @NotNull
        @Schema(description = "발주구분")
        private String asnType;

        @NotNull
        @Schema(description = "대리발주확정 유무")
        private String repYn;

        @JsonIgnore
        @Schema(description = "수정자")
        private String updUser;

    }
}