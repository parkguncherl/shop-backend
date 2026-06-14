package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Order", description = "FO order entity")
public class Order {

    private Long id;
    private String orderNo;
    private Long socialAccountId;
    private String orderStatus;
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
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
