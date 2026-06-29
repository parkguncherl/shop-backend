package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductResponseProductInfo", description = "frontWeb 이하 상품정보 응답 dto", type = "object")
    public static class ProductInfo extends Product {

        @Schema(description = "sys fileNm")
        private String sysFileNm;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductResponseProductDetail", description = "상품 상세 응답 dto", type = "object")
    public static class ProductDetail extends Product {

        @Schema(description = "대표 이미지 파일명")
        private String repSysFileNm;

        @Schema(description = "상세 이미지 파일명")
        private String detailSysFileNm;

        @Schema(description = "사이즈 이미지 파일명")
        private String sizeSysFileNm;

        @Schema(description = "기타 이미지 파일명")
        private String etcSysFileNm;

        @Schema(description = "상품 상세(SKU) 목록")
        private List<ProductDetInfo> detList;

        @Schema(description = "연관 상품 목록")
        private List<RelatedProductInfo> relatedList;
    }

    @Getter
    @Setter
    @Schema(name = "ProductResponseProductDetInfo", description = "상품 상세(SKU) 응답 dto", type = "object")
    public static class ProductDetInfo {

        @Schema(description = "상품상세 id")
        private Integer id;

        @Schema(description = "상품 id")
        private Integer productId;

        @Schema(description = "상품상세 순서")
        private Integer productDetSeq;

        @Schema(description = "사이즈")
        private String productDetSize;

        @Schema(description = "컬러")
        private String productDetColor;

        @Schema(description = "SKU 할인율")
        private BigDecimal skuDiscountRate;

        @Schema(description = "상품상세 내용")
        private String productDetCntn;

        @Schema(description = "SKU 이미지 파일명")
        private String sysFileNm;

        @Schema(description = "현재 재고 (입고P - 출고M - 주문수량)")
        private Integer stock;
    }

    @Getter
    @Setter
    @Schema(name = "ProductResponseRelatedProductInfo", description = "연관 상품 응답 dto", type = "object")
    public static class RelatedProductInfo {

        @Schema(description = "상품 id")
        private Integer id;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "판매가")
        private BigDecimal sellAmt;

        @Schema(description = "할인율")
        private BigDecimal discountRate;

        @Schema(description = "대표 이미지 파일명 (seq=1)")
        private String sysFileNm;
    }
}
