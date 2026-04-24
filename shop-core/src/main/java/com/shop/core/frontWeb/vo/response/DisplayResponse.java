package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.ProductDet;
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

        @Schema(description = "상품상세 id")
        private Integer prodDetId;

        @Schema(description = "상품상세 컬러")
        private String prodDetColor;

        @Schema(description = "상품 id")
        private Integer prodId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "판매가")
        private BigDecimal sellAmt;

    }
}
