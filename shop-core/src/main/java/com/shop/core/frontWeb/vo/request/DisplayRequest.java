package com.shop.core.frontWeb.vo.request;

import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "DisplayRequest", description = "frontWeb 메인 페이지 영역 요청")
public class DisplayRequest {

    @Getter
    @Setter
    @Schema(name = "DisplayRequestProductDetInfoListFilter", description = "메인페이지 상품정보 목록 필터")
    public static class ProductInfoListFilter implements RequestFilter {

        @Schema(description = "last 상품상세 row's id")
        private Integer lastProdDetId;
    }
}
