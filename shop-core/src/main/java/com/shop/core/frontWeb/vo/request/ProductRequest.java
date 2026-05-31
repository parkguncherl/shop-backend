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
    }
}
