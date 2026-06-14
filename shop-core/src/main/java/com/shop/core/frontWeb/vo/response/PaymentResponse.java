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
        private String payType;
        private String payMethod;
        private Long amount;
        private String pgProvider;
        private String pgTid;
        private String portonePaymentId;
        private LocalDateTime approvedTm;
    }

    @Getter
    @Setter
    @Schema(name = "PaymentResponseListItem", description = "Payment list item response", type = "object")
    public static class ListItem {
        private Long paymentSeq;
        private Long orderId;
        private String orderNo;
        private Long socialAccountId;
        private String orderStatus;
        private String orderStatusNm;
        private String paymentId;
        private String paymentStatus;
        private String paymentStatusNm;
        private Long totalAmount;
        private Long usedPoint;
        private Long paymentAmount;
        private Long earnedPoint;
        private String currency;
        private LocalDateTime paidTm;
        private LocalDateTime creTm;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String deliveryStatus;
        private String deliveryStatusNm;
        private String deliveryCompany;
        private String invoiceNo;
    }
}
