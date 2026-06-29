package com.shop.core.biz.receiving.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReceivingResponse {

    @Getter
    @Setter
    @Schema(name = "ReceivingResponseReceivingItem", description = "입고/출고 목록 항목", type = "object")
    public static class ReceivingItem {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "상품상세 id")
        private Integer productDetId;

        @Schema(description = "상품 id")
        private Integer prodId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "상품상세 사이즈")
        private String productDetSize;

        @Schema(description = "상품상세 컬러")
        private String productDetColor;

        @Schema(description = "입출고일")
        private LocalDate receivDate;

        @Schema(description = "수량")
        private Integer receivCnt;

        @Schema(description = "입출고 구분 (+: 입고, -: 출고)")
        private String plusMinus;

        @Schema(description = "비고")
        private String etcCntn;

        @Schema(description = "수정자")
        private String updUser;

        @Schema(description = "수정일시")
        private LocalDateTime updTm;
    }

    @Getter
    @Setter
    @Schema(name = "ReceivingResponseProductDetSearchItem", description = "입고용 상품상세 검색 항목", type = "object")
    public static class ProductDetSearchItem {

        @Schema(description = "상품상세 id")
        private Integer productDetId;

        @Schema(description = "상품 id")
        private Integer prodId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "상품상세 사이즈")
        private String productDetSize;

        @Schema(description = "상품상세 컬러")
        private String productDetColor;
    }
}
