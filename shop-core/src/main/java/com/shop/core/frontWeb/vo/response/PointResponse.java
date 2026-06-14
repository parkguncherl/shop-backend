package com.shop.core.frontWeb.vo.response;

import com.shop.core.enums.PointType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
        private PointType pointType;
        private Long pointAmount;    // 양수=적립, 음수=사용
        private String description;
        private LocalDateTime creTm;
    }

    @Getter
    @Setter
    @Schema(name = "PointResponseHistoryList", description = "Point history list response", type = "object")
    public static class HistoryList {
        private Long socialAccountId;
        private Long pointBalance;
        private List<History> histories;
    }
}
