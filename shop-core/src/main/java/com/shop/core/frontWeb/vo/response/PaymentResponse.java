package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentResponse {

    @Getter
    @Setter
    @Schema(name = "PaymentResponseInfo", description = "Payment response", type = "object")
    public static class Info {
        private Long paymentSeq;
        private Long orderId;
        private String orderNo;
        private String paymentId;
        private String paymentStatus;
        private Long totalAmount;
        private String currency;
        private LocalDateTime paidTm;
        private List<Det> details;
    }

    @Getter
    @Setter
    @Schema(name = "PaymentResponseDet", description = "Payment detail response", type = "object")
    public static class Det {
        private Long paymentDetId;
        private Long paymentId;
        private String paymentNo;
        private String payType;
        private String payMethod;
        private String paymentStatus;
        private Long amount;
        private String pgProvider;
        private String pgTid;
        private String portonePaymentId;
        private LocalDateTime approvedTm;
    }
}
