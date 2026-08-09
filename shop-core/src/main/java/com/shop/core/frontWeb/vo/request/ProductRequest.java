package com.shop.core.frontWeb.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ProductRequest", description = "frontWeb 이하 상품 관련 요청 dto 정의")
public class ProductRequest {

    @Getter
    @Setter
    @Schema(name = "ProductRequestProductInfoListFilter", description = "상품 목록 필터")
    public static class ProductInfoListFilter implements RequestFilter {

        @Schema(description = "last id")
        private Integer lastId;

        @Schema(description = "partner id")
        private Integer partnerId;

        @Schema(description = "카테고리 id")
        private String categoryId;

        @Schema(description = "상품유형(소분류 90011) 코드. 대분류(90010) 코드 전달 시 접두 일치로 하위 소분류 전체 조회")
        private String prodTypeCode;

        @Schema(description = "정렬 기준: PRICE_ASC | PRICE_DESC | POPULAR")
        private String sort;
    }

    @Getter
    @Setter
    @Schema(name = "ProductRequestProductSearchFilter", description = "상품 검색 필터")
    public static class ProductSearchFilter implements RequestFilter {

        @Schema(description = "검색 키워드 (상품명 또는 색상)")
        private String keyword;

        @Schema(description = "partner id")
        private Integer partnerId;

        @Schema(description = "last id")
        private Integer lastId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductRequestProductDetailParam", description = "상품 상세 조회 파라미터")
    public static class ProductDetailParam {

        @Schema(description = "상품 id")
        private Integer productId;

        @Schema(description = "partner id")
        private Integer partnerId;
    }
}
