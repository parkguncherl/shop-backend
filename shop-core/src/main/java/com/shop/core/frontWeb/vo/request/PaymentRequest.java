package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class PaymentRequest {

    @Getter
    @Setter
    @Schema(name = "PaymentRequestCreate", description = "Payment create request", type = "object")
    public static class Create {
        private Long orderId;
        private String orderNo;
        private String paymentId;
        private Long totalAmount;
        private String currency;
    }

    @Getter
    @Setter
    @Schema(name = "PaymentRequestCreateDet", description = "Payment detail create request", type = "object")
    public static class CreateDet {
        private Long paymentId;
        private String paymentNo;
        private Long orderId;
        private String orderNo;
        private String payType;
        private String payMethod;
        private Long amount;
        private String pgProvider;
        private String pgTid;
        private String portonePaymentId;
        private String rawResponse;
    }

    @Getter
    @Setter
    @Schema(name = "PaymentRequestUpdateStatus", description = "Payment status update request", type = "object")
    public static class UpdateStatus {
        private Long id;
        private String paymentStatus;
        private String failReason;
    }

    @Getter
    @Setter
    @Schema(name = "PaymentRequestUpdateDetStatus", description = "Payment detail status update request", type = "object")
    public static class UpdateDetStatus {
        private Long id;
        private String paymentStatus;
        private String pgTid;
        private String portonePaymentId;
        private String failReason;
        private String rawResponse;
    }
}
