package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class PointRequest {

    @Getter
    @Setter
    @Schema(name = "PointRequestSave", description = "Point history save request", type = "object")
    public static class Save {
        private Long socialAccountId;
        private Long orderId;
        private String paymentId;
        private String pointType;
        private Long pointAmount;
        private Long balanceAfter;
        private String description;
    }
}
