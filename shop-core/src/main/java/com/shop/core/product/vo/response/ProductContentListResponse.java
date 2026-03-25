package com.shop.core.product.vo.response;

import com.shop.core.entity.Contents;
import com.shop.core.entity.Product;
import com.shop.core.entity.ProductDet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ProductContentListResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductContentListResponseProductContent", description = "상품컨텐츠목록 영역 상품컨텐츠", type = "object")
    public static class ProductContent extends Contents {

        @Schema(description = "상품수량")
        private Integer productCnt;

        @Schema(description = "상품수량")
        private Integer imageCnt;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductContentListResponseProductInfo", description = "상품컨텐츠목록 영역 상품정보", type = "object")
    public static class ProductInfo extends Product {

        @Schema(description = "매장 식별자")
        private Integer partnerId;

        @Schema(description = "매장명")
        private String partnerNm;

        @Schema(description = "상품구분명")
        private String prodTpNm;

        @Schema(description = "원가")
        private BigDecimal orgAmt;

        @Schema(description = "판매가")
        private BigDecimal sellAmt;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductContentListResponseContentProductInfo", description = "상품컨텐츠목록 영역 연결상품 정보", type = "object")
    public static class ContentProductInfo extends ProductDet {

        @Schema(description = "연결상품 정보 id")
        private Integer contentsProductId;

        @Schema(description = "연결상품 정보 순서")
        private Integer contentsProductSeq;

        @Schema(description = "상품 id")
        private Integer prodId;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "상품소분류명")
        private String prodDetTpNm;
    }
}
