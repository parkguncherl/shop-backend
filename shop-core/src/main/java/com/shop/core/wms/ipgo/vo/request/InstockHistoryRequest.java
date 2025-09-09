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
 * 입하이력 정보 요청 클래스
 */
public class InstockHistoryRequest {

    @Getter
    @Setter
    @Schema(name = "InstockHistoryRequestPagingFilter", description = "입하이력 정보 페이징 필터")
    public static class PagingFilter {

        @Schema(description = "창고id")
        private Integer logisId;

        @Schema(description = "화주ID")
        private int partnerId;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "검색 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate startDate;

        @Schema(description = "검색 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endDate;

        @JsonIgnore
        @Schema(description = "발주구분 리스트")
        private List<String> asnTypeList;
    }

    @Getter
    @Setter
    @Schema(name = "InstockHistoryRequestDetail", description = "발주 입하이력 상세 요청")
    public static class FactoryDetail {

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
    @Schema(name = "InstockHistoryRequestReturnDetail", description = "매장분 반납 입하이력 상세 요청")
    public static class ReturnDetail {

        @NotNull
        @Schema(description = "고객사 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "반납확정일시")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime returnConfirmDtm;
    }

    @Getter
    @Setter
    @Schema(name = "InstockHistoryRequestCancelInstock", description = "입하취소 요청 파라미터")
    public static class CancelInstock {

        @NotNull
        @Schema(description = "ASN ID")
        private Integer asnId;

        @NotNull
        @Schema(description = "SKU ID")
        private Integer skuId;

        @NotNull
        @Schema(description = "물류 ID")
        private Integer logisId;

        @NotNull
        @Schema(description = "화주 ID")
        private Integer partnerId;

        @NotNull
        @Schema(description = "공장 ID")
        private Integer factoryId;

        @NotNull
        @Schema(description = "발주구분코드")
        private String asnType;

        @JsonIgnore
        @Schema(description = "발주취소자")
        private String updUser;
    }

}