package com.shop.core.biz.receiving.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ReceivingRequest {

    @Getter
    @Setter
    @Schema(name = "ReceivingRequestListFilter", description = "입고/출고 목록 조회 필터", type = "object")
    public static class ListFilter {

        @Schema(description = "조회 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fromDate;

        @Schema(description = "조회 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate toDate;

        @Schema(description = "입출고 구분 (+: 입고, -: 출고)")
        private String plusMinus;

        @Schema(description = "상품명 검색")
        private String prodNm;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "ReceivingRequestProductDetSearchFilter", description = "입고용 상품상세 검색 필터", type = "object")
    public static class ProductDetSearchFilter {

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "partnerId")
        private Integer partnerId;
    }

    @Getter
    @Setter
    @Schema(name = "ReceivingRequestInsertReceiving", description = "입고/출고 등록 요청", type = "object")
    public static class InsertReceiving {

        @Schema(description = "상품상세 id")
        private Integer productDetId;

        @Schema(description = "입출고일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate receivDate;

        @Schema(description = "수량")
        private Integer receivCnt;

        @Schema(description = "입출고 구분 (+: 입고, -: 출고)")
        private String plusMinus;

        @Schema(description = "비고")
        private String etcCntn;

        @Schema(description = "등록자")
        private String creUser;
    }

    @Getter
    @Setter
    @Schema(name = "ReceivingRequestUpdateReceiving", description = "입고/출고 수정 요청", type = "object")
    public static class UpdateReceiving {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "입출고일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate receivDate;

        @Schema(description = "수량")
        private Integer receivCnt;

        @Schema(description = "입출고 구분 (+: 입고, -: 출고)")
        private String plusMinus;

        @Schema(description = "비고")
        private String etcCntn;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(name = "ReceivingRequestDeleteReceiving", description = "입고/출고 삭제 요청", type = "object")
    public static class DeleteReceiving {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }
}
