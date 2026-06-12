package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PaymentDet", description = "FO payment detail entity")
public class PaymentDet {

    private Long id;
    private Long paymentId;
    private String paymentNo;
    private Long orderId;
    private String orderNo;
    private String payType;
    private String payMethod;
    private String paymentStatus;
    private Long amount;
    private String pgProvider;
    private String pgTid;
    private String portonePaymentId;
    private LocalDateTime approvedTm;
    private LocalDateTime cancelledTm;
    private Long cancelAmount;
    private String failReason;
    private String rawResponse;
    private String delYn;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
