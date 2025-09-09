package com.shop.core.wms.inven.vo.response;

import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Inspect;
import com.shop.core.entity.InspectDet;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InspectionInfoResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InspectionInfoResponsePaging", description = "재고 실사 정보 조회 (페이징) 응답", type = "object")
    public static class Paging {

        @Schema(description = "전체 행 수")
        private Integer totalRowCount;

        @Schema(description = "행 번호")
        private Integer no;

        @Schema(description = "ID")
        private Integer id;

        @Schema(description = "LOCATION ID")
        private Integer locId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "PARTNER ID")
        private Integer partnerId;

        @Schema(description = "고객사")
        private String partnerNm;

        @Schema(description = "신청제목")
        private String inspectTitle;

        @Schema(description = "상품건수")
        private Integer prodCnt;

        @Schema(description = "스큐건수")
        private Integer skuCnt;

        @Schema(description = "loc건수")
        private Integer locCnt;

        @Schema(description = "zone 건수")
        private Integer zoneCnt;

        @Schema(description = "빈블러 건수")
        private Integer centerCnt;

        @Schema(description = "신청시 건수")
        private Integer befCnt;

        @Schema(description = "생성일시")
        private String creTm;

        @Schema(description = "신청구분")
        private String inspectCdNm;

        @Schema(description = "신청자")
        private String createUser;

        @Schema(description = "처러상태")
        private String inspectStatCdNm;

    }

    @Getter
    @Setter
    @Schema(name = "InspectionInfoResponseDetail", description = "실사 상세 디테일 정보 조회 응답")
    public static class Detail {
        @Schema(description = "ZONE_DESC")
        private String zoneDesc;

        @Schema(description = "zoneCdNm")
        private String zoneCdNm;

        @Schema(description = "로케이션명")
        private String locAlias;

        @Schema(description = "skuNm")
        private String skuNm;

        @Schema(description = "no")
        private Integer no;

        @Schema(description = "id")
        private Integer id;

        @Schema(description = "신청시건수 ID")
        private Integer befCnt;

        @Schema(description = "수정건수")
        private Integer aftCnt;

        @Schema(description = "centerCnt")
        private Integer centerCnt;

        @Schema(description = "로케이션 상세")
        private String locCntn;

        @Schema(description = "변경여부")
        private String chgYn;

        @Schema(description = "LOCATION ID")
        private Integer locId;

        @Schema(description = "SKU ID")
        private Integer skuId;
    }
}