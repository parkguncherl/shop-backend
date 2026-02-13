package com.shop.core.product.vo.response;

import com.shop.core.entity.Contents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ProductContentListResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductContentListResponseProductContent", description = "상품컨텐츠목록 영역 상품컨텐츠", type = "object")
    public static class ProductContent extends Contents {
    }
}
