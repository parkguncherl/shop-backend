package com.shop.core.product.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductMngResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompoInfoAndProd", description = "출력시 혼용율및 상품정보", type = "object")
    public static class PrintInfo {

//        @Schema(description = "혼용율")
//        private List<CompoInfo> compoInfos;

//        @Schema(description = "상품정보")
//        private List<ProdPrintInfo> ProdPrintInfos;
    }
}
