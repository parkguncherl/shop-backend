package com.shop.core.product.vo.response;

import com.shop.core.entity.Product;
import com.shop.core.entity.ProductDet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public class ProductMngResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductInfo", description = "상품관리 영역 상품정보", type = "object")
    public static class ProductInfo extends Product {

        @Schema(description = "상품구분명")
        private String prodTpNm;

        @Schema(description = "상품상세구분명")
        private String prodDetTpNm;

        @Schema(description = "색상(종류 나열)")
        private String colors;

        @Schema(description = "사이즈(종류 나열)")
        private String sizes;

        @Schema(description = "총건수")
        private Integer totCnt;

        @Schema(description = "평균생산가")
        private BigDecimal avgOrgAmt;

        @Schema(description = "평균판매가")
        private BigDecimal avgSellAmt;

        @Schema(description = "평균실판매가")
        private BigDecimal avgRealSellAmt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductDetInfo", description = "상품관리 영역 상품상세정보", type = "object")
    public static class ProductDetInfo extends ProductDet {

        @Schema(description = "실제판매가")
        private BigDecimal realSellAmt;
    }
}
