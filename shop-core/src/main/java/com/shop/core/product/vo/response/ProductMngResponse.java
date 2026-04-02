package com.shop.core.product.vo.response;

import com.shop.core.entity.CategoryProduct;
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
    @Schema(name = "ProductMngResponseProductInfo", description = "상품관리 영역 상품정보", type = "object")
    public static class ProductInfo extends Product {

        @Schema(description = "상품구분명")
        private String prodTpNm;

        @Schema(description = "상품상세구분명")
        private String prodDetTpNm;

        @Schema(description = "색상(종류 나열)")
        private String prodColors;

        @Schema(description = "사이즈(종류 나열)")
        private String prodSizes;

        @Schema(description = "연관된 유효한 상품상세 개수")
        private Integer prodDetCnt;

        @Schema(description = "대표이미지 개수")
        private Integer repFileIdCnt;

        @Schema(description = "상세이미지 개수")
        private Integer detailFileIdCnt;

        @Schema(description = "사이즈이미지 개수")
        private Integer sizeFileIdCnt;

        @Schema(description = "기타이미지 개수")
        private Integer etcFileIdCnt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductMngResponseProductDetInfo", description = "상품관리 영역 상품상세정보", type = "object")
    public static class ProductDetInfo extends ProductDet {
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductMngResponseProductInfoByExclusion", description = "상품관리 영역 제외를 포함하는 상품정보", type = "object")
    public static class ProductInfoByExclusion extends Product {

        @Schema(description = "상품구분명")
        private String prodTpNm;

        @Schema(description = "상품상세구분명")
        private String prodDetTpNm;

        @Schema(description = "색상(종류 나열)")
        private String prodColors;

        @Schema(description = "사이즈(종류 나열)")
        private String prodSizes;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductMngResponseCategoryProductInfo", description = "카테고리 연결상품정보", type = "object")
    public static class CategoryProductInfo extends Product {

        @Schema(description = "색상(종류 나열)")
        private String prodColors;

        @Schema(description = "사이즈(종류 나열)")
        private String prodSizes;

        /** 이하 CategoryProduct 테이블 관련 */
        @Schema(description = "카테고리 연결상품정보 아이디(PK)")
        private Integer categoryProductId;

        @Schema(description = "카테고리 연결상품정보 아이디(PK)")
        private String categoryNm;

        @Schema(description = "순서")
        private Integer seq;

        @Schema(description = "카테고리 id")
        private Integer categoryId;

//        @Schema(description = "상품 id")
//        private Integer productId;
    }
}
