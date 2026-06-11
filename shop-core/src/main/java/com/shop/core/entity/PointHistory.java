package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PointHistory", description = "FO point history entity")
public class PointHistory {

    private Long id;
    private Long socialAccountId;
    private Long orderId;
    private String paymentId;
    private String pointType;
    private Long pointAmount;
    private Long balanceAfter;
    private String description;
    private LocalDateTime creTm;
}
