package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CheckoutRequest {

    @Getter
    @Setter
    @Schema(name = "CheckoutRequestCreate", description = "통합 주문/결제 요청", type = "object")
    public static class Create {

        // ── 주문 정보 ──
        private String orderNo;
        private Integer partnerId;
        private Long socialAccountId;
        private Long productAmount;
        private Long discountAmount;
        private Long usedPoint;
        private Long paymentAmount;
        private Long earnedPoint;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String memo;
        private List<OrderRequest.Item> items;

        // ── 결제 정보 ──
        private String paymentId;
        private Long totalAmount;
        private String currency;
        private List<PaymentRequest.CreateDet> details;
    }
}
