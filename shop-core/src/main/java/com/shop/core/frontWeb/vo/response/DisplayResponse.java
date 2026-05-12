package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class DisplayResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "DisplayResponseProductInfoForEnum", description = "frontWeb 메인 페이지 영역 상품정보", type = "object")
    public static class ProductInfoForEnum {

        @Schema(description = "상품상세 컬러")
        private String prodDetColor;

        @Schema(description = "상품 id")
        private Integer prodId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "판매가")
        private BigDecimal sellAmt;

        @Schema(description = "할인율")
        private BigDecimal discountRate;

        @Schema(description = "sys fileNm")
        private String sysFileNm;

    }
}
