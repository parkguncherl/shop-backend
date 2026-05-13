package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.Contents;
import com.shop.core.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ProductResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductResponseProductInfo", description = "frontWeb 이하 상품정보 응답 dto", type = "object")
    public static class ProductInfo extends Product {

        @Schema(description = "sys fileNm")
        private String sysFileNm;
    }
}
