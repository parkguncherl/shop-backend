package com.shop.core.biz.vendorProduct.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class VendorProductRequest {

    @Getter
    @Setter
    @Schema(name = "VendorProductRequestFilter", description = "협력업체 상품 조회 요청")
    public static class Filter {

        @Schema(description = "협력업체 ID")
        private Integer vendorId;

        @Schema(description = "매장(파트너) ID")
        private Integer partnerId;
    }
}
