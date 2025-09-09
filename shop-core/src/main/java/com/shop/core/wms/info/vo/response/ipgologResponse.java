package com.shop.core.wms.info.vo.response;

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
 * 적하정보 응답 클래스
 */
public class ipgologResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ipgologPaging", description = "입하정보 페이징 응답", type = "object")
    public static class Paging extends PageBaseEntity {

        @Schema(description = "입하ID(PK)")
        private Integer stockId;

        @Schema(description = "LOGIS_ID(FK)")
        private Integer logisId;

        @Schema(description = "SKU_ID(FK)")
        private Integer skuId;

        @Schema(description = "화주ID(FK)")
        private Integer partnerId;

        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "생산처(공장)")
        private String compNm;

        @Schema(description = "입하일자")
        private LocalDate stockYmd;

        @Schema(description = "입하수량")
        private Integer stockCnt;

        @Schema(description = "재고수량")
        private Integer invenCnt;

        @Schema(description = "입하구분코드명")
        private String stockCdNm;

        @Schema(description = "적치상태코드명")
        private String stockStatCdNm;

        @Schema(description = "TC유무")
        private String prodAttrCd;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "SKU 컬러")
        private String skuColor;

        @Schema(description = "SKU 사이즈")
        private String skuSize;

        @Schema(description = "적치장소")
        private String location;

        @Schema(description = "ZONE이름")
        private String zonecdNm;

        @Schema(description = "LOC이름")
        private String locationNm;

        @Schema(description = "적치번호")
        private String asnId;

        @Schema(description = "적치번호")
        private String tempNo;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "등록일시")
        protected LocalDateTime creTm;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "수정일시")
        protected LocalDateTime updTm;
    }
}