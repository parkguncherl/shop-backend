package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrderDelivery", description = "FO order delivery entity")
public class OrderDelivery {

    private Long id;
    private Long orderId;
    private String orderNo;
    private Long socialAccountId;
    private String receiverName;
    private String receiverPhone;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String memo;
    private String deliveryStatus;
    private String deliveryCompany;
    private String invoiceNo;
    private LocalDateTime shippedTm;
    private LocalDateTime deliveredTm;
    private String delYn;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
