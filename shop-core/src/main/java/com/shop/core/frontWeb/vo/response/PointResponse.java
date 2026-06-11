package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class PointResponse {

    @Getter
    @Setter
    @Schema(name = "PointResponseBalance", description = "Point balance response", type = "object")
    public static class Balance {
        private Long socialAccountId;
        private Long pointBalance;
    }

    @Getter
    @Setter
    @Schema(name = "PointResponseHistory", description = "Point history response", type = "object")
    public static class History {
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
}
