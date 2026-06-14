package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class CheckoutResponse {

    @Getter
    @Setter
    @Schema(name = "CheckoutResponseInfo", description = "통합 주문/결제 응답", type = "object")
    public static class Info {
        private Long orderId;
        private String orderNo;
        private Long paymentSeq;
        private String paymentId;
        private String paymentStatus;
        private Long totalAmount;
        private String currency;
    }
}
