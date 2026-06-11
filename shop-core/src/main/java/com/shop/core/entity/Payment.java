package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Payment", description = "FO payment header entity")
public class Payment {

    private Long id;
    private Long orderId;
    private String orderNo;
    private String paymentId;
    private String paymentStatus;
    private Long totalAmount;
    private String currency;
    private LocalDateTime paidTm;
    private LocalDateTime cancelledTm;
    private Long cancelAmount;
    private String failReason;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
