package com.shop.core.entity;

import com.shop.core.enums.PointType;
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
    private PointType pointType;
    private Long pointAmount;    // 양수=적립, 음수=사용
    private String description;
    private LocalDateTime creTm;
}
