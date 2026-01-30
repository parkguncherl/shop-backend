package com.shop.core.product.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Schema(name = "ProductMngRequest", description = "상품관리 영역 요청")
public class ProductMngRequest {

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestProductInfoFilter", description = "상품 정보 필터")
    public static class ProductInfoFilter implements RequestFilter {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestProductDetInfoFilter", description = "상품 상세 정보 필터")
    public static class ProductDetInfoFilter implements RequestFilter {
    }
}
